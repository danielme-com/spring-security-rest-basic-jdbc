package com.danielme.springsecuritybasic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestRestController.TEST_RESOURCE)
public class TestRestController {

    public static final String TEST_RESOURCE = "/test";

    //@PreAuthorize("isAuthenticated()")
    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void testEndpoint() {
        // nothing
    }

}
