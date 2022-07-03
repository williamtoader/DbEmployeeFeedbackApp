package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.repositories.EmailRepository;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The EmailConfirmationService is responsible
 * for creating and sending the email confirmation link.
 */
@RequiredArgsConstructor
@Service
public class EmailConfirmationService {
    final EmailRepository emailRepository;
    final MailerService mailerService;

    /*
     * Creates a new email and sends a confirmation email to the user.
     * @param address the email address of the user.
     * @return the email.
     * @throws MailjetException if the email could not be sent.
     */
    public Email createNewEmail(String address) throws MailjetException {
        String token = UUID.randomUUID().toString();

        mailerService.sendConfirmationMail(address, token);

        return emailRepository.save(Email.builder()
                        .address(address)
                        .token(token)
                        .confirmed(false)
                        .identity(null)
                .build());
    }

    /**
     * Confirm the email address.
     *
     * @param token The token to confirm the email address.
     * @return The email address.
     */
    public Email confirmEmail(String token) {
        Email email = emailRepository.getEmailByToken(token);
        email.setConfirmed(true);
        return emailRepository.save(email);
    }
}
