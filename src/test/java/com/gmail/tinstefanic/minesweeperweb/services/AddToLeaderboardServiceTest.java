package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.GameBoard;
import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import com.gmail.tinstefanic.minesweeperweb.repositories.GameBoardRepository;
import com.gmail.tinstefanic.minesweeperweb.repositories.LeaderboardEntryRepository;
import com.gmail.tinstefanic.minesweeperweb.util.GameDifficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class AddToLeaderboardServiceTest {

    @MockBean
    private LeaderboardEntryRepository leaderboardEntryRepository;

    @MockBean
    private GameBoardRepository gameBoardRepository;

    private AddToLeaderboardService addToLeaderboardService;

    @BeforeEach
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
        int width = GameDifficulty.EASY.getWidth(), height = GameDifficulty.EASY.getHeight();
        int totalMines = GameDifficulty.EASY.getTotalMines();
        when(this.gameBoardRepository.existsById(id)).thenReturn(false);
        when(this.gameBoardRepository.findById(id)).thenReturn(Optional.of(new GameBoard(width, height, totalMines)));

        this.addToLeaderboardService.addToLeaderBoard(id);

        verify(this.leaderboardEntryRepository).save(any(LeaderboardEntry.class));
    }
}