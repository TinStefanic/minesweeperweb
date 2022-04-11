package com.gmail.tinstefanic.minesweeperweb.repositories;

import com.gmail.tinstefanic.minesweeperweb.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}