package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.repositories.LeaderboardEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Gets leaderboard entries from database that fit given criteria.
 */
@Service
public class FilterLeaderboardService {

    private final LeaderboardEntryRepository leaderboardEntryRepository;

    @Autowired
    public FilterLeaderboardService(LeaderboardEntryRepository leaderboardEntryRepository) {
        this.leaderboardEntryRepository = leaderboardEntryRepository;
    }

    /**
     * Returns users last numEntries results, or fewer if they didn't play numEntries games.
     * @param username Name of the user.
     * @param numEntries Maximum number of entries to return.
     * @return List of leaderboard entries, ordered from youngest to oldest.
     */
    @Transactional(readOnly = true)
    public List<LeaderboardEntry> queryUserHistory(String username, int numEntries) {
        return this.leaderboardEntryRepository
                .findAllByUsernameOrderByCompletionDateTimeDesc(username, PageRequest.of(0, numEntries));
    }

    /**
     * Returns recent entries from all users and difficulties, up to maximum of numEntries entries.
     * @param numEntries Maximum number of entries to return.
     * @return List of leaderboard entries, ordered from youngest to oldest.
     */
    @Transactional(readOnly = true)
    public List<LeaderboardEntry> queryRecent(int numEntries) {
        return this.leaderboardEntryRepository
                .findAllByOrderByCompletionDateTimeDesc(PageRequest.of(0, numEntries));
    }

    /**
     * Returns entries with the fastest completion time for provided difficulty.
     * @param difficulty Name of difficulty by which to query.
     * @param numEntries Maximum number of entries to return.
     * @return List of leaderboard entries, ordered from the fastest completion time to slowest.
     */
    @Transactional(readOnly = true)
    public List<LeaderboardEntry> queryByDifficulty(String difficulty, int numEntries) {
        return this.leaderboardEntryRepository
                .findAllByDifficultyOrderByGameDurationMillisAsc(difficulty, PageRequest.of(0, numEntries));
    }
}
