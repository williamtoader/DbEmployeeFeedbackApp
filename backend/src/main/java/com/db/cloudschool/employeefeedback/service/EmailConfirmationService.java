package com.db.cloudschool.employeefeedback.service;

import com.db.cloudschool.employeefeedback.model.Email;
import com.db.cloudschool.employeefeedback.repositories.EmailRepository;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmailConfirmationService {
    EmailRepository emailRepository;
    MailerService mailerService;

    public Email createNewEmail(String address) throws MailjetException {
        String token = UUID.randomUUID().toString();

        mailerService.sendConfirmationMail(address, token);

        return emailRepository.save(Email.builder()
                        .token(token)
                        .confirmed(false)
                        .identity(null)
                .build());
    }

    public Email confirmEmail(String token) {
        Email email = emailRepository.getEmailByToken(token);
        email.setConfirmed(true);
        return emailRepository.save(email);
    }
}
