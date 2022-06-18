package com.backend.admin.repository;

import com.backend.admin.model.Logs;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<Logs, ObjectId> {

}
