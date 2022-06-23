package com.backend.myhouse.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.myhouse.model.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    List<Message> findAllByDevice_Id(ObjectId id);
}
