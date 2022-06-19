package com.backend.admin.repository;

import com.backend.admin.model.Household;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseholdRepository extends MongoRepository<Household, ObjectId> {
    Optional<Household> findByName(String name);
}
