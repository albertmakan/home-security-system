package com.backend.admin.service;

import com.backend.admin.model.Household;
import com.backend.admin.repository.HouseholdRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;

    public Optional<Household> findById(ObjectId id) {
        return householdRepository.findById(id);
    }

    public List<Household> getAll() {
        return householdRepository.findAll();
    }
}
