package com.project.shopapp.controllers;

import com.project.shopapp.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser() {
        return ResponseEntity.ok("User registered successfully.");
    }

    @GetMapping("")
    public ResponseEntity<String> getAllUser() {
        return ResponseEntity.ok("Users retrieved successfully.");
    }

    @PostMapping("/details")
    public ResponseEntity<String> getUserDetails() {
        return ResponseEntity.ok("User details retrieved successfully.");
    }

    @PutMapping("/details/{userId}")
    public ResponseEntity<String> updateUserDetails(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok("User details updated successfully. userId = " + userId);
    }

    @PostMapping("/upload-profile-image")
    public ResponseEntity<String> uploadProfileImage() {
        return ResponseEntity.ok("Profile image uploaded successfully.");
    }

    @GetMapping("/profile-images/{imageName}")
    public ResponseEntity<String> viewImage(@PathVariable("imageName") String imageName) {
        return ResponseEntity.ok("Profile image retrieved successfully. imageName = " + imageName);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("User logged in successfully.");
    }

    @PostMapping("/login/social")
    public ResponseEntity<String> loginSocial() {
        return ResponseEntity.ok("User logged in with social account successfully.");
    }

    @GetMapping("/auth/social/callback")
    public ResponseEntity<String> callback(
            @RequestParam("code") String authorizationCode,
            @RequestParam("loginType") String loginType
    ) {
        return ResponseEntity.ok("Social login callback processed successfully.");
    }

    @GetMapping("/auth/social-login")
    public ResponseEntity<String> socialAuth(@RequestParam("loginType") String loginType) {
        return ResponseEntity.ok("Social login initiated successfully. loginType = " + loginType);
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<String> resetPassword(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok("Password reset successfully. userId = " + userId);
    }

    @PutMapping("/block/{userId}/{active}")
    public ResponseEntity<String> blockOrEnable(
            @PathVariable("userId") Long userId,
            @PathVariable("active") Integer active
    ) {
        String status = (active == 1) ? "enabled" : "blocked";
        return ResponseEntity.ok("User " + status + " successfully. userId = " + userId);
    }

    public boolean isMobileDevice(String userAgent) {
        return userAgent.toLowerCase().contains("mobile");
    }

}
