package com.backend.myhouse.repository;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.myhouse.model.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    @Query("{ 'timestamp' : { $gte: ?0, $lte: ?1 }, 'deviceId' : { $in: ?2 }, 'message' : /?3/i }")
    List<Message> findAllByDeviceIdsBetweenDatesWithFilter(Date from, Date to, List<ObjectId> deviceIds, String filter);
}
