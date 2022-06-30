package com.db.cloudschool.employeefeedback.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The MailerService is responsible for sending emails.
 * It uses the Mailjet API to send emails.
 */
@RequiredArgsConstructor
@Service
public class MailerService {
    final String emailConfirmationEndpoint = System.getenv("EFA_EMAIL_TOKEN_ENDPOINT");


    final ClientOptions options = ClientOptions.builder()
            .apiKey(System.getenv("MJ_APIKEY_PUBLIC"))
            .apiSecretKey(System.getenv("MJ_APIKEY_PRIVATE"))
            .build();

    final MailjetClient client = new MailjetClient(options);

    /*
     * Send the confirmation email to the user.
     *
     * @param address The email address of the user.
     * @param token The token to confirm the email address.
     * @throws MailjetException if the email could not be sent.
     */
    public void sendConfirmationMail(String emailAddress, String token) throws MailjetException {
        TransactionalEmail message = TransactionalEmail
                .builder()
                .to(new SendContact(emailAddress))
                .from(new SendContact("accounts@efa-app.ml"))
                .htmlPart(String.format("Link <a href=\"%s\">%s</a>", emailConfirmationEndpoint + token, token))
                .subject("DB Employee Feedback: Please confirm your e-mail address")
                .build();
        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message) // you can add up to 50 messages per request
                .build();
        request.sendWith(client);
    }
}
