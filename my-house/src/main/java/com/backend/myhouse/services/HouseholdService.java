package com.backend.myhouse.services;

import com.backend.myhouse.model.Household;
import com.backend.myhouse.repository.HouseholdRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;

    public Optional<Household> findById(ObjectId id) {
        return householdRepository.findById(id);
    }

}
