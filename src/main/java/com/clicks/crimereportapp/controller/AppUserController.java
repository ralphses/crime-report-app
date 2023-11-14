package com.clicks.crimereportapp.controller;

import com.clicks.crimereportapp.dto.requests.LoginRequest;
import com.clicks.crimereportapp.service.AppUserService;
import com.clicks.crimereportapp.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class AppUserController {

    private final AppUserService userService;

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody LoginRequest loginRequest) {
        boolean loginSuccess = userService.login(loginRequest);
        return ResponseEntity.ok(new CustomResponse("SUCCESS", loginSuccess));
    }
}
