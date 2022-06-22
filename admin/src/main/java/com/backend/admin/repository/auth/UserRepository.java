package com.backend.admin.repository.auth;

import java.util.List;
import java.util.Optional;

import com.backend.admin.model.auth.Role;
import com.backend.admin.model.auth.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String name);

    @Query("{ 'username' : { $regex : ?0, $options : 'i' }}, ") //TODO ROLES , 'roles.id.oid' : ?1 
    List<User> findByUsernameRegexAndRole(String keyword);


}
