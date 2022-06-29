package com.backend.admin.repository;

import com.backend.admin.model.rules.AlarmRule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRuleRepository extends MongoRepository<AlarmRule, ObjectId> {
}
