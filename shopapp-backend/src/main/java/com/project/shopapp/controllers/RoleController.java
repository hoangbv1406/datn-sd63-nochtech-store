package com.project.shopapp.controllers;

import com.project.shopapp.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<String> getAllRoles() {
        return ResponseEntity.ok("Roles retrieved successfully.");
    }

}
