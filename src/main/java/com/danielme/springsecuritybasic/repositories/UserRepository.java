package com.danielme.springsecuritybasic.repositories;

import com.danielme.springsecuritybasic.model.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByName(String name);
}
