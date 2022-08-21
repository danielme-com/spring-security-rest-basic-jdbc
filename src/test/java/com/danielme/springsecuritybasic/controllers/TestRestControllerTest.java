package com.danielme.springsecuritybasic.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAnonymous() throws Exception {
        mockMvc.perform(get(TestRestController.TEST_RESOURCE))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void testAnonymousButWrongCredentials() throws Exception {
        mockMvc.perform(get(TestRestController.TEST_RESOURCE).with(httpBasic("anything", "anything")))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

}