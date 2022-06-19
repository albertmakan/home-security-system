package com.backend.admin.repository;

import com.backend.admin.model.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<Log, ObjectId> {

}
