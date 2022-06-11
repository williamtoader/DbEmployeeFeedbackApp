package com.db.cloudschool.employeefeedback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTest {
    @GetMapping("/hello")
    public String hello() {
        return "Hello wrld!";
    }
}
