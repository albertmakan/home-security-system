package com.backend.myhouse.repository;

import com.backend.myhouse.model.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<Log, ObjectId> {

}
