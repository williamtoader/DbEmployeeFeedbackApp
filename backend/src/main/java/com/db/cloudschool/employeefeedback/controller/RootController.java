package com.db.cloudschool.employeefeedback.controller;

import com.db.cloudschool.employeefeedback.util.ResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Locale;

@RestController
@RequestMapping("/")
public class RootController {
    /** Gets version string from properties indicating type of deployment
     */
    @Value("${efa.meta.version}")
    private String version;


    /**
     * Endpoint responding with a version string used for tracing bugs back to specific build
     * @return Backend version string built as follows: active profile string + build timestamp + author name (that we get automatically from git commit in CD pipeline)
     */
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
