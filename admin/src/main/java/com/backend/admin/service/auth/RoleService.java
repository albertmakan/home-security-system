package com.backend.admin.service.auth;

import com.backend.admin.model.auth.Role;
import com.backend.admin.repository.auth.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}

