package com.project.shopapp.controllers;

import com.project.shopapp.components.SecurityUtils;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.dtos.UserUpdateDTO;
import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.user.LoginResponse;
import com.project.shopapp.responses.user.UserResponse;
import com.project.shopapp.services.auth.AuthService;
import com.project.shopapp.services.token.TokenService;
import com.project.shopapp.services.user.UserService;
import com.project.shopapp.utils.FileUtils;
import com.project.shopapp.utils.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthService authService;
    private final SecurityUtils securityUtils;

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
    public ResponseEntity<ResponseObject> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User details retrieved successfully.")
                .data(UserResponse.fromUser(user))
                .status(HttpStatus.OK)
                .build()
        );
    }

    @PutMapping("/details/{userId}")
    public ResponseEntity<ResponseObject> updateUserDetails(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateDTO userUpdateDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7);
        User user = userService.getUserDetailsFromToken(extractedToken);
        if (user.getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User updatedUser = userService.updateUser(userId, userUpdateDTO);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User details updated successfully. userId = " + userId)
                .data(UserResponse.fromUser(updatedUser))
                .status(HttpStatus.OK)
                .build()
        );
    }

    @PostMapping(value = "/upload-profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadProfileImage(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        User loginUser = securityUtils.getLoggedInUser();
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message("Image file is required.")
                    .build()
            );
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(ResponseObject.builder()
                    .message("Image file size exceeds the allowed limit of 10MB.")
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .build()
            );
        }
        if (!FileUtils.isImageFile(file)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ResponseObject.builder()
                    .message("Uploaded file must be an image.")
                    .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .build()
            );
        }
        // Store file and get filename
        String oldFileName = loginUser.getProfileImage();
        String imageName = FileUtils.storeFile(file);

        userService.changeProfileImage(loginUser.getId(), imageName);
        if (!StringUtils.isEmpty(oldFileName)) {
            FileUtils.deleteFile(oldFileName);
        }

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Profile image uploaded successfully.")
                .status(HttpStatus.CREATED)
                .data(imageName)
                .build()
        );
    }

    @GetMapping("/profile-images/{imageName}")
    public ResponseEntity<String> viewImage(@PathVariable("imageName") String imageName) {
        return ResponseEntity.ok("Profile image retrieved successfully. imageName = " + imageName);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            HttpServletRequest request
    ) throws Exception {
        String token = userService.login(userLoginDTO);
        String userAgent = request.getHeader("User-Agent");
        User userDetail = userService.getUserDetailsFromToken(token);
        Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

        LoginResponse loginResponse = LoginResponse.builder()
                .message("")
                .token(jwtToken.getToken())
                .tokenType(jwtToken.getTokenType())
                .refreshToken(jwtToken.getRefreshToken())
                .username(userDetail.getUsername())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .id(userDetail.getId())
                .build();
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User logged in successfully.")
                .data(loginResponse)
                .status(HttpStatus.OK)
                .build()
        );
    }

    @PostMapping("/login/social")
    public ResponseEntity<ResponseObject> loginSocial(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            HttpServletRequest request
    ) throws Exception {
        String token = userService.loginSocial(userLoginDTO);
        String userAgent = request.getHeader("User-Agent");
        User userDetail = userService.getUserDetailsFromToken(token);
        Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

        LoginResponse loginResponse = LoginResponse.builder()
                .message("LOGIN SUCCESSFULLY")
                .token(jwtToken.getToken())
                .tokenType(jwtToken.getTokenType())
                .refreshToken(jwtToken.getRefreshToken())
                .username(userDetail.getUsername())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .id(userDetail.getId())
                .build();
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User logged in with social account successfully.")
                .data(loginResponse)
                .status(HttpStatus.OK)
                .build()
        );
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
