package com.gmail.tinstefanic.minesweeperweb.repositories;

import com.gmail.tinstefanic.minesweeperweb.entities.LeaderboardEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardEntryRepository extends PagingAndSortingRepository<LeaderboardEntry, Long> {
}