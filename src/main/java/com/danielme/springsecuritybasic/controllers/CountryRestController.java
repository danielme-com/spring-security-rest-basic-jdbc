package com.danielme.springsecuritybasic.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielme.springsecuritybasic.model.Country;
import com.danielme.springsecuritybasic.services.CountryService;

@RestController
@RequestMapping(CountryRestController.COUNTRY_RESOURCE)
public class CountryRestController {

    public static final String COUNTRY_RESOURCE = "/country";

    private static final Logger logger = LoggerFactory.getLogger(CountryRestController.class);

    private final CountryService countryService;

    public CountryRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "/{id}/")
    public ResponseEntity<Country> getById(@PathVariable("id") Long id) {
        printUser(SecurityContextHolder.getContext().getAuthentication());
        Optional<Country> country = countryService.findById(id);
        if (country.isPresent()) {
            return new ResponseEntity<>(country.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id, Authentication authentication) {
        printUser(authentication);
        countryService.deleteById(id);
    }

    private void printUser(Authentication authentication) {
        authentication.getAuthorities().forEach(a -> logger.info(a.getAuthority()));
        logger.info(authentication.getName());
    }
}
