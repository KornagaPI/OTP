package com.frol97.codes.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.frol97.codes.dto.DeleteUserRequest;
import com.frol97.codes.dto.GetUserResponse;
import com.frol97.codes.dto.ResponseWithMessage;
import com.frol97.codes.model.Role;
import com.frol97.codes.model.entity.User;
import com.frol97.codes.model.repo.UserRepo;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserService {
    private final UserRepo repo;
    private final OtpService otpService;

    @Autowired
    public UserService(UserRepo repo, OtpService otpService) {
        this.repo = repo;
        this.otpService = otpService;
    }

    public User save(User user) {
        return repo.save(user);
    }

    public User create(User user) {
        if (repo.existsByLogin(user.getLogin())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        return save(user);
    }

    public User getByLogin(String login) {
        return repo.findByLogin(login).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByLogin;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByLogin(username);
    }

    public List<GetUserResponse> getUsersInfo() {
        var users = StreamSupport.stream(repo.findAll().spliterator(), false).filter(e -> e.getRole() != Role.ADMIN).toList();
        return users.stream().map(u -> {
            var userOtps = otpService.getUserOtps(u.getLogin());
            return new GetUserResponse(u.getLogin(), u.getRole().name(), userOtps);
        }).toList();
    }

    public ResponseEntity<ResponseWithMessage> deleteUser(DeleteUserRequest deleteUserRequest) {
        var login = deleteUserRequest.login;
        otpService.deleteOtpsForUser(login);
        var usersCount = repo.deleteByLogin(login);
        if (usersCount > 0) return ResponseEntity
                .ok()
                .body(new ResponseWithMessage("Пользователь [" + login + "] удален"));
        else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseWithMessage("Пользователь [" + login + "] не найден"));
    }

    public boolean isAdminExists() {
        return repo.existsByRole(Role.ADMIN);
    }
}
