package com.db.cloudschool.employeefeedback.security.service;

import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.repositories.EmailRepository;
import com.db.cloudschool.employeefeedback.security.model.Credentials;
import com.db.cloudschool.employeefeedback.security.repository.CredentialsRepository;
import com.db.cloudschool.employeefeedback.service.IdentityService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * @author Wiliam Toader
 */
@Service
@RequiredArgsConstructor
public class CredentialsService {
    private final Charset keyStringEncoding = StandardCharsets.UTF_16;

    private final EmailRepository emailRepository;
    private final CredentialsRepository credentialsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretsService secretsService;
    private final IdentityService identityService;

    private final Long REFRESH_JWT_EXPIRATION_MS = 1000L * 3600L * 24L * 30L;
    private final Long ACCESS_JWT_EXPIRATION_MS = 1000L * 3600L * 24L;

    SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            Credentials credentials = credentialsRepository.findByEmail(claims.getSubject());
            if(credentials == null) return null;
            else return Keys.hmacShaKeyFor(credentials.getRefreshTokenKey().getBytes(keyStringEncoding));
        }
    };

    public Credentials addCredentials(@NotNull Credentials credentials) throws EmailAddressNotConfirmedException, IdentityNotFoundException {
//        if(!emailRepository.existsByAddress(credentials.getEmail()))
//            throw new UsernameNotFoundException("Email not found in database");
        //credentialsRepository.save(
        return credentialsRepository.save(credentials);
    }

    public Credentials generateCredentials(String emailAddress, String plainTextPassword, Identity identity) {
        String encodedKey = new String(
                Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded(),
                keyStringEncoding
        );
        return new Credentials(
                emailAddress,
                bCryptPasswordEncoder.encode(plainTextPassword),
                encodedKey,
                identity
        );
    }
    public void resetRefreshTokenSignatureKey(String email) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        if(credentials == null) throw new UsernameNotFoundException("Credentials not found for email");
        credentials.setRefreshTokenKey(new String(
                Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded(),
                keyStringEncoding)
        );
        credentialsRepository.save(credentials);
    }

    public Boolean validatePassword(String email, String password) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        if(credentials == null) return false;
        else return bCryptPasswordEncoder.matches(password, credentials.getPasswordHash());
    }

    @Transactional
    public Identity validateRefreshToken(@NotNull String refreshToken) throws JwtException, EmailAddressNotConfirmedException, IdentityNotFoundException {
//        String[] splitToken = refreshToken.split("\\.");
//        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKeyResolver(signingKeyResolver)
                .build().parseClaimsJws(refreshToken);
        String email = jws.getBody().getSubject();
        Credentials credentials = checkSubject(email);
        checkExpiration(jws);

        return credentials.getIdentity();
    }

    @Transactional
    public Identity validateAccessToken(String accessToken) throws JwtException, EmailAddressNotConfirmedException, IdentityNotFoundException {
//        String[] splitToken = accessToken.split("\\.");
//        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(
                secretsService.getAccessTokenSignatureKey()
        ).build().parseClaimsJws(accessToken);

        String email = jws.getBody().getSubject();
        Credentials credentials = checkSubject(email);
        checkExpiration(jws);

        return credentials.getIdentity();
    }

    private void checkExpiration(Jws<Claims> jws) {
        if (jws.getBody().getExpiration().before(new Date())) throw new JwtException("Expired");
    }

    @NotNull
    private Credentials checkSubject(String emailAddress) throws EmailAddressNotConfirmedException, IdentityNotFoundException {
        Identity identity = identityService.getIdentity(emailAddress);

        if(identity == null) throw new JwtException("Subject not found");


        return identity.getCredentials();
    }

    public String generateAccessToken(String email) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        if(credentials == null) throw new UsernameNotFoundException("Email not found");
        Key key = secretsService.getAccessTokenSignatureKey();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_JWT_EXPIRATION_MS))
                .signWith(key).compact();
    }

    public String generateRefreshToken(String email) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        if(credentials == null) throw new UsernameNotFoundException("Email not found");
        Key key = Keys.hmacShaKeyFor(credentials.getRefreshTokenKey().getBytes(keyStringEncoding));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_JWT_EXPIRATION_MS))
                .signWith(key).compact();
    }
}
