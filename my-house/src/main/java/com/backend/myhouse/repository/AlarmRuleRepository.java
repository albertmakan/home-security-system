package com.backend.myhouse.repository;

import com.backend.myhouse.model.rules.AlarmRule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRuleRepository extends MongoRepository<AlarmRule, ObjectId> {
}
