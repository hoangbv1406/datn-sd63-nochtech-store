package com.project.shopapp.configurations;

import com.project.shopapp.models.User;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return subject -> {
            Optional<User> userByPhoneNumber = userRepository.findByPhoneNumber(subject);
            if (userByPhoneNumber.isPresent()) {
                return userByPhoneNumber.get();
            }
            Optional<User> userByEmail = userRepository.findByEmail(subject);
            if (userByEmail.isPresent()) {
                return userByEmail.get();
            }
            throw new UsernameNotFoundException("User not found with subject: " + subject);
        };
    }

}
