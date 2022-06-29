package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.model.Profile;
import com.db.cloudschool.employeefeedback.repositories.IdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The IdentityService can be considered
 * "the user service", as it manages the central identity
 * used by all other services to identify
 * users, admins and all other API consumers.
 * The Identity is like an identifier
 * that links back to all the other connected services.
 * @author Wiliam Toader
 */
@Service
@RequiredArgsConstructor
public class IdentityService {
    private IdentityRepository identityRepository;

    /**
     * This function will throw typed Exception if the identity
     * requested should for any reason not be considered valid
     * for output;
     * @param identity Identity object considered valid.
     * @throws EmailAddressNotConfirmedException
     * Email address of the identity was not confirmed
     * so identity should not be considered valid.
     * @throws IdentityNotFoundException
     * Identity asserted as valid was found to be null.
     */
    private void assertIsValidIdentityOutput(Identity identity) throws
            EmailAddressNotConfirmedException,
            IdentityNotFoundException
    {
        // Check identity exists
        if (identity == null)
            throw new IdentityNotFoundException();
        // Check if email address is confirmed
        if (!identity.getEmail().getConfirmed())
            throw new EmailAddressNotConfirmedException();
    }

    /**
     * @param email Email object connected to identity.
     * @return Identity of Email object.
     * @throws EmailAddressNotConfirmedException
     * Email address of the identity was not confirmed
     * so identity should not be considered valid.
     * @throws IdentityNotFoundException
     * Identity asserted as valid was found to be null.
     */
    public Identity getIdentity(Email email) throws
            EmailAddressNotConfirmedException,
            IdentityNotFoundException {
        Identity identity = identityRepository.findByEmail(email);

        assertIsValidIdentityOutput(identity);

        return identity;
    }


    /**
     * @param emailAddress Email address connected to identity.
     * @return Identity of Email address.
     * @throws EmailAddressNotConfirmedException
     * Email address of the identity was not confirmed
     * so identity should not be considered valid.
     * @throws IdentityNotFoundException
     * Identity asserted as valid was found to be null.
     */
    public Identity getIdentity(String emailAddress) throws
            EmailAddressNotConfirmedException,
            IdentityNotFoundException {
        Identity identity = identityRepository.findByEmail_Address(emailAddress);

        assertIsValidIdentityOutput(identity);

        return identity;
    }

    /**
     * @param profile Profile object connected to identity.
     * @return Identity of Profile object.
     * @throws EmailAddressNotConfirmedException
     * Email address of the identity was not confirmed
     * so identity should not be considered valid.
     * @throws IdentityNotFoundException
     * Identity asserted as valid was found to be null.
     */
    public Identity getIdentity(Profile profile) throws
            EmailAddressNotConfirmedException,
            IdentityNotFoundException
    {
        Identity identity = identityRepository.findByProfile(profile);

        assertIsValidIdentityOutput(identity);

        return identity;
    }

    /**
     * Proceeds to delete Identity from database
     * by id from given object
     */
    public Long deleteIdentity(Identity identity) {
        return deleteIdentity(identity.getApiUserId());
    }

    /**
     * Proceeds to delete Identity from database by id
     */
    public Long deleteIdentity(Long apiUserId) {
        identityRepository.deleteById(apiUserId);

        return apiUserId;
    }

    /**
     * Used for creating/updating Identity object in database
     * @param identity Identity object to be persisted.
     *                 For creating new object apiUserId should be null.
     * @return Identity object with all missing fields completed.
     */
    public Identity saveIdentity(Identity identity) {
        return identityRepository.save(identity);
    }
}
