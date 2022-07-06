package com.db.cloudschool.employeefeedback;

import com.db.cloudschool.employeefeedback.dto.RegisterDTO;
import com.db.cloudschool.employeefeedback.exceptions.EmailAddressNotConfirmedException;
import com.db.cloudschool.employeefeedback.exceptions.IdentityNotFoundException;
import com.db.cloudschool.employeefeedback.model.Identity;
import com.db.cloudschool.employeefeedback.service.EmailConfirmationService;
import com.db.cloudschool.employeefeedback.service.IdentityService;
import com.db.cloudschool.employeefeedback.service.MailerService;
import com.db.cloudschool.employeefeedback.service.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Naming:
 * MethodName_StateUnderTest_ExpectedBehavior
 * example: isAdult_AgeLessThan18_False
 */
@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@RequiredArgsConstructor
public class RegistrationServiceTests {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    IdentityService identityService;
    @Autowired
    MailerService mailerService;
    @Autowired
    EmailConfirmationService emailConfirmationService;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @Transactional
    public void registerIdentity_HardcodedData_PopulateTables()
            throws EmailAddressNotConfirmedException, IdentityNotFoundException, MailjetException, JsonProcessingException {
        Identity identity = registrationService.registerIdentity(RegisterDTO.builder()
                .emailAddress("wiliamtoader@protonmail.com")
                .biography("Dev at DB")
                .password("1234")
                .firstName("Wiliam")
                .lastName("Toader")
                .build());
        System.out.println(objectMapper.writeValueAsString(identity));
    }
}
