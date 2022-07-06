package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.service.EmailConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailConfirmationController {

    private final EmailConfirmationService emailConfirmationService;

    @GetMapping("/email/confirm/{token}")
    public String confirmEmail(@PathVariable String token) {
        try {
            emailConfirmationService.confirmEmail(token);
            return "Email confirmed successfuly.";
        }
        catch (Exception exception) {
            return "Email confirmation failure.";
        }
    }
}
