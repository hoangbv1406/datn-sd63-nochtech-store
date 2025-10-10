package com.project.shopapp.responses.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserListResponse {

    private List<UserResponse> users;
    private int totalPages;

}
