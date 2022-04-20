package com.gmail.tinstefanic.minesweeperweb.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Given authenticated user accessing difficulty, should return status 200.")
    @WithMockUser
    void givenAuthenticatedUserAccessingDifficulty_shouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/difficulty"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given authenticated user accessing game/easy, should return status 200.")
    @WithMockUser
    void givenAuthenticatedUserAccessingGameEasy_shouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/game/easy"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given authenticated user accessing game/normal, should return status 200.")
    @WithMockUser
    void givenAuthenticatedUserAccessingGameNormal_shouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/game/normal"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given authenticated user accessing game/hard, should return status 200.")
    @WithMockUser
    void givenAuthenticatedUserAccessingGameHard_shouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/game/hard"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given authenticated user accessing game/notDifficulty, should redirect to menu.")
    @WithMockUser
    void givenAuthenticatedUserAccessingGameNotDifficulty_shouldRedirectToMenuTest() throws Exception {
        this.mockMvc.perform(get("/game/notDifficulty"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu"));
    }
}