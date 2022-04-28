package com.gmail.tinstefanic.minesweeperweb.repositories;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardEntryRepository extends PagingAndSortingRepository<LeaderboardEntry, Long> {

    List<LeaderboardEntry> findAllByUsernameOrderByCompletionDateTimeDesc(String username, Pageable pageable);

    List<LeaderboardEntry> findAllByOrderByCompletionDateTimeDesc(Pageable pageable);

    List<LeaderboardEntry> findAllByDifficultyOrderByGameDurationMillisAsc(String difficulty, Pageable pageable);
}