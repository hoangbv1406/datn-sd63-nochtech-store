package com.project.shopapp.services.user;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(UserDTO userDTO) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
}
