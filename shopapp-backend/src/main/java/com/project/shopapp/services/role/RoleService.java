package com.project.shopapp.services.role;

import com.project.shopapp.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleServiceImpl {
    private final RoleRepository roleRepository;
}
