package com.db.cloudschool.employeefeedback.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
    @Value("${efa.meta.version}")
    private String version;

    @GetMapping

    public String getAppRoot() {
        return "Running EFA backend version: " + version;
    }
}
