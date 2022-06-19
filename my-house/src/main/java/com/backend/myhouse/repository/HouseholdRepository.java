package com.backend.myhouse.repository;

import com.backend.myhouse.model.Household;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseholdRepository extends MongoRepository<Household, ObjectId> {
    Optional<Household> findByName(String name);
}
