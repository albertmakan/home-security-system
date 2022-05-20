package com.backend.admin.repository.auth;

import java.util.List;

import com.backend.admin.model.auth.RevokedToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevokedTokensRepository extends MongoRepository<RevokedToken, ObjectId> {

    RevokedToken findByToken(String token); //TODO change to List<> ?
}

