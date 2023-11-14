package com.clicks.crimereportapp.service;

import com.clicks.crimereportapp.dto.requests.LoginRequest;
import com.clicks.crimereportapp.exceptions.InvalidParamsException;
import com.clicks.crimereportapp.model.AppUser;
import com.clicks.crimereportapp.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;

    public boolean login(LoginRequest loginRequest) {
        AppUser user = getUserByUsername(loginRequest.username());
        return user.getPassword().equals(loginRequest.password());
    }

    private AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidParamsException("User with username " + username + " NOT FOUND"));
    }
}
