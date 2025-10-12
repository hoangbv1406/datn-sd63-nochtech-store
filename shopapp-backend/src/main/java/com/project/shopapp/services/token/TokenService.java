package com.project.shopapp.services.token;

import com.project.shopapp.models.Token;
import com.project.shopapp.models.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
}
