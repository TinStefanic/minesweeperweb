package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.services.FilterLeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class LeaderboardController {

    private final FilterLeaderboardService filterLeaderboardService;
    private final Integer pageSize;

    @Autowired
    public LeaderboardController(
            FilterLeaderboardService filterLeaderboardService,
            @Value("${settings.leaderboard.page-size}") Integer pageSize)
    {
        this.filterLeaderboardService = filterLeaderboardService;
        this.pageSize = pageSize;
    }

    @GetMapping({"leaderboard", "/leaderboard/{difficulty}"})
    public String leaderboard(
            @PathVariable(value = "difficulty", required = false) String difficulty, Model model)
    {
        List<LeaderboardEntry> entryList = null;

        if (difficulty != null) {
            entryList = this.filterLeaderboardService.queryByDifficulty(difficulty, this.pageSize);
        }

        model.addAttribute("entryList", entryList);

        return "leaderboard";
    }

    @GetMapping("history")
    public String personalHistory(Model model, Principal principal) {
        List<LeaderboardEntry> historyList =
                this.filterLeaderboardService.queryUserHistory(principal.getName(), this.pageSize);
        model.addAttribute("recentList", historyList);

        return "recent";
    }

    @GetMapping("recent")
    public String allUsersRecent(Model model) {
        List<LeaderboardEntry> recentList =
                this.filterLeaderboardService.queryRecent(this.pageSize);
        model.addAttribute("recentList", recentList);

        return "recent";
    }
}
