package com.bektas.youtubeclone.controller;

import com.bektas.youtubeclone.dto.VideoDto;
import com.bektas.youtubeclone.service.UserRegistrationService;
import com.bektas.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(Authentication authentication) {
        System.out.println("Register");
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String userId = userRegistrationService.registerUser(jwt.getTokenValue());
        System.out.println("ID: " + userId);
        return userId;
    }

    @PostMapping("/subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean subscribeToUser(@PathVariable String userId) {
        userService.subscribeToUser(userId);
        return true;
    }

    @PostMapping("/unsubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unsubscribeToUser(@PathVariable String userId) {
        userService.unsubscribeToUser(userId);
        return true;
    }

    @GetMapping("/{userId}/history")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getUserHistory(@PathVariable String userId) {
        return userService.userHistory(userId);
    }
}
