package com.danielme.springsecuritybasic.controllers;

import com.danielme.springsecuritybasic.model.Country;
import com.danielme.springsecuritybasic.services.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CountryRestController.COUNTRIES_RESOURCE)
public class CountryRestController {

    public static final String COUNTRIES_RESOURCE = "/countries";
    public static final String COUNTRIES_ID_PATH = "/{id}";

    private static final Logger logger = LoggerFactory.getLogger(CountryRestController.class);

    private final CountryService countryService;


    public CountryRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = COUNTRIES_ID_PATH)
    public ResponseEntity<Country> getById(@PathVariable("id") Long id) {
        logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = COUNTRIES_ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id, Authentication authentication) {
        logger.info(authentication.getName());
        countryService.deleteById(id);
    }

}