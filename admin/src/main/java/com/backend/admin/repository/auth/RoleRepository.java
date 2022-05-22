package com.backend.admin.repository.auth;

import java.util.List;

import com.backend.admin.model.auth.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    List<Role> findByName(String name);
}
