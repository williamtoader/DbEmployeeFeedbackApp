package com.db.cloudschool.employeefeedback;

import com.db.cloudschool.employeefeedback.service.EmailConfirmationService;
import com.db.cloudschool.employeefeedback.service.IdentityService;
import com.db.cloudschool.employeefeedback.service.MailerService;
import com.db.cloudschool.employeefeedback.service.OnboardRegisterService;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
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
public class OnboardRegisterServiceTests {
    @Autowired
    OnboardRegisterService onboardRegisterService;
    @Autowired
    IdentityService identityService;
    @Autowired
    MailerService mailerService;
    @Autowired
    EmailConfirmationService emailConfirmationService;



    @Test
    @After
    void registerIdentity_HardcodedData_PopulateTables() {
        System.out.println("Hello");
    }
}
