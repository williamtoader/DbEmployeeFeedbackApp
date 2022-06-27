package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.util.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

@RestController
@RequestMapping("/")
public class RootController {
    /** Gets build version
     */
    @Value("${efa.meta.version}")
    private String version;



    @GetMapping

    public String getAppRoot() {
        try {
            return "Running EFA backend version: " + version + "-" +
                    ResourceLoader.getResourceFileAsString("author-id.txt")
                            .toUpperCase(Locale.ROOT)
                            .replace(' ', '-');
        } catch (RuntimeException exception) {
            return "Running EFA backend version: " + version + "-" + "UNKNOWN-AUTHOR";
        }
    }
}
