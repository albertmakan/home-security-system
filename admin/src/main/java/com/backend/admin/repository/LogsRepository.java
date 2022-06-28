package com.backend.admin.repository;

import com.backend.admin.model.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LogsRepository extends MongoRepository<Log, ObjectId> {

    @Query("{_id: {$gt: ?0, $lt: ?1}, level: ?2, message: {$regex: ?3, $options: 'i'}}")
    List<Log> getForDayByLevelAndFilter(ObjectId minId, ObjectId max, String level, String filter);

    @Query("{_id: {$gt: ?0, $lt: ?1}, message: {$regex: ?2, $options: 'i'}}}")
    List<Log> getForDayByFilter(ObjectId minId, ObjectId max, String filter);
}
