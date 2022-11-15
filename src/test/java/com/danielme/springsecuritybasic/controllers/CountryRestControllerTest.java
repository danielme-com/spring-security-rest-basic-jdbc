package com.danielme.springsecuritybasic.controllers;

import com.danielme.springsecuritybasic.services.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountryRestControllerTest {

    private static final long MEXICO_ID = 2;
    private static final String USER_STANDARD = "user";
    private static final String USER_ADMIN = "admin";
    public static final String URL = CountryRestController.COUNTRIES_RESOURCE + CountryRestController.COUNTRIES_ID_PATH;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CountryService countryService;

    @Test
    void testGetCountryNoAuthenticacion() throws Exception {
        mockMvc.perform(get(URL, MEXICO_ID))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    void testGetCountryWrongUser() throws Exception {
        mockMvc.perform(get(URL, MEXICO_ID)
                .with(httpBasic(USER_STANDARD, "password")))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    void testGetCountrySuccess() throws Exception {
        mockMvc.perform(get(URL, MEXICO_ID)
                .with(httpBasic(USER_STANDARD, USER_STANDARD)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.name", is(countryService.findById(MEXICO_ID).get().getName())));
    }

    @Test
    @WithMockUser
    void testGetCountrySuccessMockUser() throws Exception {
        mockMvc.perform(get(URL, MEXICO_ID))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.name", is(countryService.findById(MEXICO_ID).get().getName())));
    }

    @Test
    void testDeleteCountryWrongAuthorization() throws Exception {
        mockMvc.perform(delete(URL, MEXICO_ID)
                .with(httpBasic(USER_STANDARD, USER_STANDARD)))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    void testDeleteCountrySuccessful() throws Exception {
        mockMvc.perform(delete(URL, -1)
                .with(httpBasic(USER_ADMIN, USER_ADMIN)))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

}