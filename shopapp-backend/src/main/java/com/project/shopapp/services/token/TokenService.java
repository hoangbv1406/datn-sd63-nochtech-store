package com.project.shopapp.services.token;

import com.project.shopapp.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService implements TokenServiceImpl {
    private final TokenRepository tokenRepository;
}
