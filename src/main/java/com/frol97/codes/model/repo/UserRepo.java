package com.frol97.codes.model.repo;

import org.springframework.data.repository.CrudRepository;

import com.frol97.codes.model.Role;
import com.frol97.codes.model.entity.User;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    long deleteByLogin(String login);
    boolean existsByRole(Role role);
}
