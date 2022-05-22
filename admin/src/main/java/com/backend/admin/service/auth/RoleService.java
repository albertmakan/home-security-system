package com.backend.admin.service.auth;
import java.util.List;
import java.util.Optional;

import com.backend.admin.model.auth.Role;
import com.backend.admin.repository.auth.RoleRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RoleService {

  @Autowired
  private RoleRepository roleRepository;

  public Optional<Role> findById(ObjectId id) {
    Optional<Role> auth = this.roleRepository.findById(id);
    return auth;
  }

  public List<Role> findByName(String name) {
	List<Role> roles = this.roleRepository.findByName(name);
    return roles;
  }


}

