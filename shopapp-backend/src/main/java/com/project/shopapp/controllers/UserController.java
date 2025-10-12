package com.project.shopapp.controllers;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.services.auth.AuthService;
import com.project.shopapp.services.token.TokenService;
import com.project.shopapp.services.user.UserService;
import com.project.shopapp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthService authService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUser() {
        List<User> user = userService.getAllUsers();
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Users retrieved successfully.")
                .status(HttpStatus.OK)
                .data(user)
                .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(errorMessages.toString())
                    .build()
            );
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isBlank()) {
            if (userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().isBlank()) {
                return ResponseEntity.badRequest().body(ResponseObject.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .data(null)
                        .message("At least email or phone number is required")
                        .build()
                );
            } else {
                if (!ValidationUtils.isValidPhoneNumber(userDTO.getPhoneNumber())) {
                    throw new Exception("Invalid phone number");
                }
            }
        } else {
            if (!ValidationUtils.isValidEmail(userDTO.getEmail())) {
                throw new Exception("Invalid email format");
            }
        }
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message("Passwords must match.")
                    .build()
            );
        }
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(user)
                .message("User registered successfully.")
                .build()
        );
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
