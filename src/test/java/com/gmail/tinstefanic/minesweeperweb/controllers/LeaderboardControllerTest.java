package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.services.FilterLeaderboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LeaderboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilterLeaderboardService filterLeaderboardService;

    @BeforeEach
    private void setUp() {
        var leaderboardEntry = new LeaderboardEntry();
        leaderboardEntry.setUsername("user");
        leaderboardEntry.setId(13L);
        leaderboardEntry.setDifficulty("easy");
        leaderboardEntry.setGameDurationMillis(5000L);
        int year = 2022, month = 5, dayOfMonth = 2, hour = 0, minute = 0;
        leaderboardEntry.setCompletionDateTime(LocalDateTime.of(year, month, dayOfMonth, hour, minute));
        var mockReturnList = List.of(leaderboardEntry);

        when(this.filterLeaderboardService.queryUserHistory(anyString(), anyInt())).thenReturn(mockReturnList);
        when(this.filterLeaderboardService.queryByDifficulty(anyString(), anyInt())).thenReturn(mockReturnList);
        when(this.filterLeaderboardService.queryRecent(anyInt())).thenReturn(mockReturnList);
    }

    @Test
    @DisplayName("Accessing leaderboard should return status 200.")
    @WithMockUser
    void accessingLeaderboardShouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/leaderboard")).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"easy", "normal", "hard"})
    @DisplayName("Accessing leaderboard/{difficulty} should return status 200.")
    @WithMockUser
    void accessingLeaderboardDifficultyShouldReturnStatus200Test(String difficulty) throws Exception {
        this.mockMvc.perform(get("/leaderboard/" + difficulty)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Accessing personal history should return status 200.")
    @WithMockUser
    void accessingPersonalHistoryShouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/history")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Accessing all users' recent results should return status 200.")
    @WithMockUser
    void accessingAllUsersRecentResultsShouldReturnStatus200Test() throws Exception {
        this.mockMvc.perform(get("/recent")).andExpect(status().isOk());
    }
}