package com.db.cloudschool.employeefeedback.security.service;

import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.repositories.EmailRepository;
import com.db.cloudschool.employeefeedback.security.model.Credentials;
import com.db.cloudschool.employeefeedback.security.repository.CredentialsRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

/**
 * @author Wiliam Toader
 */
@Service
@RequiredArgsConstructor
public class CredentialsService {
    private final EmailRepository emailRepository;
    private final CredentialsRepository credentialsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretsService secretsService;

    private final Long REFRESH_JWT_EXPIRATION_MS = 1000L * 3600L * 24L * 30L;
    private final Long ACCESS_JWT_EXPIRATION_MS = 1000L * 3600L * 24L;

    public void addCredentials(@NotNull Credentials credentials) throws EmailAddressNotConfirmedException, IdentityNotFoundException {
        if(!emailRepository.existsById(credentials.getEmail()))
            throw new UsernameNotFoundException("Email not found in database");
        credentialsRepository.save(credentials);
    }

    public Credentials generateCredentials(String emailAddress, String plainTextPassword) {
        String encodedKey = new String(
                Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded(),
                StandardCharsets.UTF_8
        );
        return new Credentials(
                emailAddress,
                bCryptPasswordEncoder.encode(plainTextPassword),
                encodedKey
        );
    }
    public void resetRefreshTokenSignatureKey(String email) {
        Optional<Credentials> credentialsOptional = credentialsRepository.findById("email");
        if(credentialsOptional.isEmpty()) throw new UsernameNotFoundException("Credentials not found for email");
        Credentials credentials = credentialsOptional.get();
        credentials.setRefreshTokenKey(new String(
                Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded(),
                StandardCharsets.UTF_8)
        );
        credentialsRepository.save(credentials);
    }

    public Boolean validatePassword(String email, String password) {
        Credentials credentials = credentialsRepository.findById(email).orElse(null);
        if(credentials == null) return false;
        else return bCryptPasswordEncoder.matches(password, credentials.getPasswordHash());
    }

    public Boolean validateRefreshToken(@NotNull String refreshToken) {
        String[] splitToken = refreshToken.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        try {
            String email = Jwts.parserBuilder().build()
                    .parseClaimsJws(unsignedToken)
                    .getBody()
                    .getSubject();
            Credentials credentials = checkSubject(email);

            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(
                    credentials.getRefreshTokenKey().getBytes(StandardCharsets.UTF_8)
            ).build().parseClaimsJws(unsignedToken);

            checkExpiration(jws);

        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    public Identity validateAccessToken(String accessToken) throws JwtException{
        String[] splitToken = accessToken.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        Identity identity = null;

        String email = Jwts.parserBuilder().build()
                .parseClaimsJws(unsignedToken)
                .getBody()
                .getSubject();

        Credentials credentials = checkSubject(email);

        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(
                secretsService.getAccessTokenSignatureKey()
        ).build().parseClaimsJws(unsignedToken);

        checkExpiration(jws);
        identity = credentials.getIdentity();
        return identity;
    }

    private void checkExpiration(Jws<Claims> jws) {
        if (jws.getBody().getExpiration().before(new Date())) throw new JwtException("Expired");
    }

    @NotNull
    private Credentials checkSubject(String email) {
        Credentials credentials = credentialsRepository.findById(email).orElse(null);

        if(credentials == null) throw new JwtException("Subject not found");

        Optional<Email> emailOptional = emailRepository.findById(credentials.getEmail());

        if(emailOptional.isEmpty()) throw new JwtException("Subject not found");

        if(emailOptional.get().getConfirmed()) throw new JwtException("Subject not confirmed");

        return credentials;
    }

    public String generateAccessToken(String email) {
        Credentials credentials = credentialsRepository.findById(email).orElse(null);
        if(credentials == null) throw new UsernameNotFoundException("Email not found");
        Key key = secretsService.getAccessTokenSignatureKey();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_JWT_EXPIRATION_MS))
                .signWith(key).compact();
    }

    public String generateRefreshToken(String email) {
        Credentials credentials = credentialsRepository.findById(email).orElse(null);
        if(credentials == null) throw new UsernameNotFoundException("Email not found");
        Key key = Keys.hmacShaKeyFor(credentials.getRefreshTokenKey().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_JWT_EXPIRATION_MS))
                .signWith(key).compact();
    }
}
