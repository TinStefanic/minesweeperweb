package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.repositories.LeaderboardEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FilterLeaderboardServiceTest {

    @Autowired
    LeaderboardEntryRepository leaderboardEntryRepository;

    @Autowired
    FilterLeaderboardService filterLeaderboardService;

    Long totalNumEntries = 0L;
    int allAndMoreEntries = 100; // Number larger than total amount of entries.

    @BeforeEach
    void setUp() {
        int year = 2022, month = 4, dayOfMonth = 28, hour = 13, minute = 13, second = 13;
        var referenceTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        String user1 = "user1", user2 = "user2";
        String easy = "easy", normal = "normal", hard = "hard";

        // user1 entries, 12 in total.
        createAndAddEntryToDatabase(user1, easy, 100, referenceTime);
        createAndAddEntryToDatabase(user1, easy, 50, referenceTime.plusDays(1));
        createAndAddEntryToDatabase(user1, easy, 50, referenceTime.plusDays(2));
        createAndAddEntryToDatabase(user1, easy, 50, referenceTime.plusDays(3));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(1));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(2));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(3));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(4));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(5));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(6));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(7));
        createAndAddEntryToDatabase(user1, normal, 100, referenceTime.plusDays(3).plusHours(8));

        // user2 entries, 4 in total.
        createAndAddEntryToDatabase(user2, normal, 50, referenceTime);
        createAndAddEntryToDatabase(user2, hard, 50, referenceTime.plusDays(1));
        createAndAddEntryToDatabase(user2, hard, 50, referenceTime.plusDays(2));
        createAndAddEntryToDatabase(user2, hard, 50, referenceTime.plusDays(3));
    }

    private void createAndAddEntryToDatabase(
            String username, String difficulty, long gameDurationSeconds, LocalDateTime localDateTime)
    {
        var createdEntry = new LeaderboardEntry();

        createdEntry.setUsername(username);
        createdEntry.setDifficulty(difficulty);
        createdEntry.setId(++totalNumEntries);
        createdEntry.setGameDurationMillis(gameDurationSeconds * 1000);
        createdEntry.setCompletionDateTime(localDateTime);

        this.leaderboardEntryRepository.save(createdEntry);
    }

    @ParameterizedTest
    @CsvSource({"user1,12", "user2,4"})
    @DisplayName("Should return all entries from user.")
    void shouldReturnAllEntriesFromUserTest(String username, int expectedNumEntries) {
        List<LeaderboardEntry> userHistory =
                filterLeaderboardService.queryUserHistory(username, this.allAndMoreEntries);

        assertThat(userHistory.size()).isEqualTo(expectedNumEntries);
    }

    @ParameterizedTest
    @CsvSource({"user1,7", "user2,3"})
    @DisplayName("Should return provided amount of entries from user.")
    void shouldReturnProvidedAmountOfEntriesFromUserTest(String username, int numEntriesLimit) {
        List<LeaderboardEntry> userHistory =
                filterLeaderboardService.queryUserHistory(username, numEntriesLimit);

        assertThat(userHistory.size()).isEqualTo(numEntriesLimit);
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1", "user2"})
    @DisplayName("Should return entries from user sorted by datetime descending.")
    void shouldReturnEntriesFromUserSortedByDatetimeDescendingTest(String username) {
        List<LeaderboardEntry> userHistory =
                filterLeaderboardService.queryUserHistory(username, this.allAndMoreEntries);

        assertThat(userHistory).isSortedAccordingTo(
                Comparator.comparing(LeaderboardEntry::getCompletionDateTime).reversed()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1", "user2"})
    @DisplayName("Should return entries from provided user only.")
    void shouldReturnEntriesFromProvidedUserOnlyTest(String username) {
        List<LeaderboardEntry> userHistory =
                filterLeaderboardService.queryUserHistory(username, this.allAndMoreEntries);

        assertThat(userHistory).allSatisfy(
                le -> assertThat(le.getUsername()).isEqualTo(username)
        );
    }

    @Test
    @DisplayName("Should return all entries.")
    void shouldReturnAllEntriesTest() {
        List<LeaderboardEntry> recent =
                filterLeaderboardService.queryRecent(this.allAndMoreEntries);

        assertThat((long)recent.size()).isEqualTo(this.totalNumEntries);
    }

    @Test
    @DisplayName("Should return entries sorted by datetime descending.")
    void shouldReturnEntriesSortedByDatetimeDescendingTest() {
        List<LeaderboardEntry> recent =
                filterLeaderboardService.queryRecent(this.allAndMoreEntries);

        assertThat(recent).isSortedAccordingTo(
                Comparator.comparing(LeaderboardEntry::getCompletionDateTime).reversed()
        );
    }

    @ParameterizedTest
    @CsvSource({"easy,4", "normal,9", "hard,3"})
    @DisplayName("Should return provided amount of entries.")
    void shouldReturnProvidedAmountOfEntriesTest(String difficulty, int expectedAmount) {
        List<LeaderboardEntry> byDifficulty =
                filterLeaderboardService.queryByDifficulty(difficulty, this.allAndMoreEntries);

        assertThat(byDifficulty.size()).isEqualTo(expectedAmount);
    }

    @ParameterizedTest
    @ValueSource(strings = {"easy", "normal", "hard"})
    @DisplayName("Should return entries of correct difficulty.")
    void shouldReturnEntriesOfCorrectDifficultyTest(String difficulty) {
        List<LeaderboardEntry> byDifficulty =
                filterLeaderboardService.queryByDifficulty(difficulty, this.allAndMoreEntries);

        assertThat(byDifficulty).allSatisfy(
                le -> assertThat(le.getDifficulty()).isEqualTo(difficulty)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"easy", "normal", "hard"})
    @DisplayName("Should return entries sorted by completion time ascending.")
    void shouldReturnEntriesSortedByCompletionTimeAscendingTest(String difficulty) {
        List<LeaderboardEntry> byDifficulty =
                filterLeaderboardService.queryByDifficulty(difficulty, this.allAndMoreEntries);

        assertThat(byDifficulty).isSortedAccordingTo(
                Comparator.comparing(LeaderboardEntry::getGameDurationMillis)
        );
    }
}