package com.backend.admin.service;

import com.backend.admin.exception.NotFoundException;
import com.backend.admin.model.Household;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.HouseholdRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Household save(Household household) {
        return householdRepository.save(household);
    }

    public Household addUserToHousehold(ObjectId householdId, User user) {
        Household household = findById(householdId).orElseThrow(() -> new NotFoundException("Household not found"));
        if (household.getUsers() == null) household.setUsers(new ArrayList<>());
        if (household.getUsers().stream().anyMatch(u -> user.getId().equals(u.getId()))) return household;
        household.getUsers().add(user);
        return save(household);
    }

    public Household removeUserFromHousehold(ObjectId householdId, ObjectId userId) {
        Household household = findById(householdId).orElseThrow(() -> new NotFoundException("Household not found"));
        if (household.getUsers() == null) return household;
        household.getUsers().removeIf(u -> userId.equals(u.getId()));
        return save(household);
    }


}
