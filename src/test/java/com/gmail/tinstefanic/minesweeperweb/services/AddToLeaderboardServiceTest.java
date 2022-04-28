package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.repositories.LeaderboardEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AddToLeaderboardServiceTest {

    @Mock
    private LeaderboardEntryRepository leaderboardEntryRepository;

    @Mock
    private GameBoardRepository gameBoardRepository;

    private AddToLeaderboardService addToLeaderboardService;

    @Before
    public void setUp() {
        this.addToLeaderboardService =
                new AddToLeaderboardService(this.leaderboardEntryRepository, this.gameBoardRepository);
    }

    @Test
    @DisplayName("When entry with same GameBoard id is already present then shouldn't add new entry.")
    void whenEntryWithSameGameBoardIdIsAlreadyPresentThenShouldntAddNewEntryTest() {
        long id = 13;
        when(this.gameBoardRepository.existsById(id)).thenReturn(true);

        this.addToLeaderboardService.addToLeaderBoard(id);

        verify(this.leaderboardEntryRepository, never()).save(any(LeaderboardEntry.class));
    }

    @Test
    @DisplayName("When entry with same GameBoard id isn't already present then should add new entry.")
    void whenEntryWithSameGameBoardIdIsntAlreadyPresentThenShouldAddNewEntryTest() {
        long id = 13;
        when(this.gameBoardRepository.existsById(id)).thenReturn(false);

        this.addToLeaderboardService.addToLeaderBoard(id);

        verify(this.leaderboardEntryRepository).save(any(LeaderboardEntry.class));
    }
}