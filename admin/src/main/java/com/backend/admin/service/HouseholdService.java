package com.backend.admin.service;

import com.backend.admin.dto.DeviceRequest;

import com.backend.admin.dto.HouseholdRequest;
import com.backend.admin.dto.mq.NewDevice;
import com.backend.admin.exception.BadRequestException;
import com.backend.admin.exception.NotFoundException;
import com.backend.admin.model.Device;
import com.backend.admin.model.Household;
import com.backend.admin.model.auth.User;
import com.backend.admin.repository.HouseholdRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.security.InvalidAlgorithmParameterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private KafkaTemplate<String, NewDevice> kafkaTemplate;

    public Optional<Household> findById(ObjectId id) {
        return householdRepository.findById(id);
    }

    public List<Household> getAll() {
        return householdRepository.findAll();
    }

    public Household save(Household household) {
        return householdRepository.save(household);
    }

    public void addUserToHousehold(ObjectId householdId, User user) {
        Household household = findById(householdId).orElseThrow(() -> new NotFoundException("Household not found"));
        if (household.getUsers() == null)
            household.setUsers(new ArrayList<>());
        if (household.getUsers().stream().anyMatch(u -> user.getId().equals(u.getId())))
            return;
        household.getUsers().add(user);
        save(household);
    }

    public void removeUserFromHousehold(ObjectId householdId, ObjectId userId) {
        Household household = findById(householdId).orElseThrow(() -> new NotFoundException("Household not found"));
        if (household.getUsers() == null)
            return;
        household.getUsers().removeIf(u -> userId.equals(u.getId()));
        save(household);
    }

    public Household create(HouseholdRequest request) {
        if (householdRepository.findByName(request.getName()).isPresent())
            throw new BadRequestException("Household name must be unique");
        Household household = new Household();
        household.setName(request.getName());
        return save(household);
    }

    public Household addDevice(DeviceRequest request) throws InvalidAlgorithmParameterException {
        Household household = findById(request.getHouseholdId())
                .orElseThrow(() -> new NotFoundException("Household not found"));
        if (household.getDevices() == null)
            household.setDevices(new ArrayList<>());
        Device device = new Device();
        device.setName(request.getName());
        device.setFilter(request.getFilter());
        device.setPath(request.getPath());
        device.setPeriod(request.getPeriod());
        device.setId(new ObjectId());
        device.setPublicKey(request.getPublicKey());

        household.getDevices().add(device);
        save(household);

        System.out.println("HH " + household.getId() + " " + request.getHouseholdId());
        System.out.println("DEV " + device.getId());
        kafkaTemplate.send("NEW_DEVICE", new NewDevice(request.getHouseholdId(), device.getId(), true));

        return household;
    }

    public Household removeDevice(ObjectId houseId, ObjectId deviceId) {
        Household household = findById(houseId).orElseThrow(() -> new NotFoundException("Household not found"));
        if (household.getDevices() == null)
            household.setDevices(new ArrayList<>());
        if (!household.getDevices().removeIf(device -> deviceId.equals(device.getId())))
            throw new NotFoundException("Device not found");
        return save(household);
    }
}
