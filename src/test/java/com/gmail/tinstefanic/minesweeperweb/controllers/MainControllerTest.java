package com.gmail.tinstefanic.minesweeperweb.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Given unauthenticated user, should redirect to login page.")
    void givenUnauthenticatedUser_shouldRedirectToLoginPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @DisplayName("Given authenticated user, should redirect to menu page.")
    @WithMockUser
    void givenAuthenticatedUser_shouldRedirectToMenuPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("menu"));
    }

    @Test
    @DisplayName("Given authenticated user accessing login page, should redirect to menu page.")
    @WithMockUser
    void givenAuthenticatedUserAccessingLoginPage_shouldRedirectToMenuPageTest() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("menu"));
    }

    @Test
    @DisplayName("Given unauthenticated user accessing login page, should return status 200.")
    void givenUnauthenticatedUserAccessingLoginPage_shouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given unauthenticated user accessing menu page, should redirect to login page.")
    void givenUnauthenticatedUserAccessingMenuPage_shouldRedirectToLoginPageTest() throws Exception {
        this.mockMvc.perform(get("/menu"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @DisplayName("Given authenticated user accessing menu page, should return status 200.")
    @WithMockUser
    void givenAuthenticatedUserAccessingMenuPage_shouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/menu"))
                .andExpect(status().isOk());
    }
}