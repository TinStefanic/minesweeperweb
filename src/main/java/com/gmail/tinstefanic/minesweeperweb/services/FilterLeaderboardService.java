package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.exceptions.NotImplementedException;
import com.gmail.tinstefanic.minesweeperweb.repositories.LeaderboardEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Test.
// TODO: Implement.
/**
 * Gets leaderboard entries from database that fit given criteria.
 */
@Service
public class FilterLeaderboardService {

    private LeaderboardEntryRepository leaderboardEntryRepository;

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
    public List<LeaderboardEntry> queryUserHistory(String username, int numEntries) {
        throw new NotImplementedException();
    }

    /**
     * Returns recent entries from all users and difficulties, up to maximum of numEntries entries.
     * @param numEntries Maximum number of entries to return.
     * @return List of leaderboard entries, ordered from youngest to oldest.
     */
    public List<LeaderboardEntry> queryRecent(int numEntries) {
        throw new NotImplementedException();
    }

    /**
     * Returns entries with the fastest completion time for provided difficulty.
     * @param difficulty Name of difficulty by which to query.
     * @param numEntries Maximum number of entries to return.
     * @return List of leaderboard entries, ordered from the fastest completion time to slowest.
     */
    public List<LeaderboardEntry> queryByDifficulty(String difficulty, int numEntries) {
        throw new NotImplementedException();
    }
}
