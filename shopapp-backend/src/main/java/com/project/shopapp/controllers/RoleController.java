package com.project.shopapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @GetMapping("")
    public ResponseEntity<String> getAllRoles() {
        return ResponseEntity.ok("Roles retrieved successfully.");
    }

}
