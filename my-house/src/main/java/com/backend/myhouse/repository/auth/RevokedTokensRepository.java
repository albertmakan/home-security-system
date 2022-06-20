package com.backend.myhouse.repository.auth;

import com.backend.myhouse.model.auth.RevokedToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevokedTokensRepository extends MongoRepository<RevokedToken, ObjectId> {
    Optional<RevokedToken> findByToken(String token);
}

