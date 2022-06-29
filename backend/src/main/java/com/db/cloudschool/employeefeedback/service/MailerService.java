package com.db.cloudschool.employeefeedback.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailerService {
    String emailConfirmationEndpoint = System.getenv("EFA_EMAIL_TOKEN_ENDPOINT");

    ClientOptions options = ClientOptions.builder()
            .apiKey(System.getenv("MJ_APIKEY_PUBLIC"))
            .apiSecretKey(System.getenv("MJ_APIKEY_PRIVATE"))
            .build();

    MailjetClient client = new MailjetClient(options);

    public SendEmailsResponse sendConfirmationMail(String emailAddress, String token) throws MailjetException {
        TransactionalEmail message = TransactionalEmail
                .builder()
                .to(new SendContact(emailAddress))
                .from(new SendContact("accounts@efa-app.ml"))
                .htmlPart(String.format("Link <a href=\"%s\">%s</a>", token, token))
                .subject("This is the subject")
                .build();
        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message) // you can add up to 50 messages per request
                .build();
        return request.sendWith(client);
    }
}
