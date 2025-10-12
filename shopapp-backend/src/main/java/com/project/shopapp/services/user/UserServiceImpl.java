package com.project.shopapp.services.user;

import com.project.shopapp.components.JwtTokenUtils;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.dtos.UserUpdateDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.ExpiredTokenException;
import com.project.shopapp.exceptions.InvalidPasswordException;
import com.project.shopapp.exceptions.PermissionDenyException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.TokenRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        if (!userDTO.getPhoneNumber().isBlank() && userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Phone number is already registered.");
        }
        if (!userDTO.getEmail().isBlank() && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email address is already registered.");
        }
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new DataNotFoundException("Role not found."));
        if (role.getName().equalsIgnoreCase(Role.ADMIN)) {
            throw new PermissionDenyException("Assigning ADMIN role is not allowed.");
        }
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .active(true)
                .build();
        newUser.setRole(role);
        if (!userDTO.isSocialLogin()) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String subject = jwtTokenUtil.getSubject(token);
        Optional<User> user;
        user = userRepository.findByPhoneNumber(subject);
        if (user.isEmpty() && ValidationUtils.isValidEmail(subject)) {
            user = userRepository.findByEmail(subject);
        }
        return user.orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        Optional<User> optionalUser = Optional.empty();

        if (userLoginDTO.getPhoneNumber() != null && !userLoginDTO.getPhoneNumber().isBlank()) {
            optionalUser = userRepository.findByPhoneNumber(userLoginDTO.getPhoneNumber());
        }
        if (optionalUser.isEmpty() && userLoginDTO.getEmail() != null) {
            optionalUser = userRepository.findByEmail(userLoginDTO.getEmail());
        }
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("");
        }

        User existingUser = optionalUser.get();
        if (!existingUser.isActive()) {
            throw new DataNotFoundException("");
        }

        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public String loginSocial(UserLoginDTO userLoginDTO) throws Exception {
        Optional<User> optionalUser = Optional.empty();
        Role roleUser = roleRepository.findByName(Role.USER).orElseThrow(() -> new DataNotFoundException(""));

        // Google Account ID
        if (userLoginDTO.isGoogleAccountIdValid()) {
            optionalUser = userRepository.findByGoogleAccountId(userLoginDTO.getGoogleAccountId());
            if (optionalUser.isEmpty()) {
                User newUser = User.builder()
                        .fullName(Optional.ofNullable(userLoginDTO.getFullname()).orElse(""))
                        .email(Optional.ofNullable(userLoginDTO.getEmail()).orElse(""))
                        .profileImage(Optional.ofNullable(userLoginDTO.getProfileImage()).orElse(""))
                        .role(roleUser)
                        .googleAccountId(userLoginDTO.getGoogleAccountId())
                        .password("")
                        .active(true)
                        .build();
                newUser = userRepository.save(newUser);
                optionalUser = Optional.of(newUser);
            }
        }
        // Facebook Account ID
        else if (userLoginDTO.isFacebookAccountIdValid()) {
            optionalUser = userRepository.findByFacebookAccountId(userLoginDTO.getFacebookAccountId());
            if (optionalUser.isEmpty()) {
                User newUser = User.builder()
                        .fullName(Optional.ofNullable(userLoginDTO.getFullname()).orElse(""))
                        .email(Optional.ofNullable(userLoginDTO.getEmail()).orElse(""))
                        .profileImage(Optional.ofNullable(userLoginDTO.getProfileImage()).orElse(""))
                        .role(roleUser)
                        .facebookAccountId(userLoginDTO.getFacebookAccountId())
                        .password("")
                        .active(true)
                        .build();
                newUser = userRepository.save(newUser);
                optionalUser = Optional.of(newUser);
            }
        } else {
            throw new IllegalArgumentException("Invalid social account information.");
        }

        User user = optionalUser.get();
        if (!user.isActive()) {
            throw new DataNotFoundException("");
        }

        return jwtTokenUtil.generateToken(user);
    }

    @Override
    public User updateUser(Long userId, UserUpdateDTO userUpdateDTO) throws Exception {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        if (userUpdateDTO.getFullName() != null) {
            existingUser.setFullName(userUpdateDTO.getFullName());
        }
        if (userUpdateDTO.getAddress() != null) {
            existingUser.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        }
        if (userUpdateDTO.isFacebookAccountIdValid()) {
            existingUser.setFacebookAccountId(userUpdateDTO.getFacebookAccountId());
        }
        if (userUpdateDTO.isGoogleAccountIdValid()) {
            existingUser.setGoogleAccountId(userUpdateDTO.getGoogleAccountId());
        }

        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            if (!userUpdateDTO.getPassword().equals(userUpdateDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            String newPassword = userUpdateDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void changeProfileImage(Long userId, String imageName) throws Exception {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        existingUser.setProfileImage(imageName);
        userRepository.save(existingUser);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws DataNotFoundException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        existingUser.setPassword(encodedPassword);
        userRepository.save(existingUser);
        List<Token> tokens = tokenRepository.findByUser(existingUser);
        for (Token token : tokens) {
            tokenRepository.delete(token);
        }
    }

}
