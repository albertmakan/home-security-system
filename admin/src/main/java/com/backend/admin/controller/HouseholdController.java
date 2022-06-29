package com.backend.admin.controller;

import java.security.InvalidAlgorithmParameterException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.admin.dto.DeviceRequest;
import com.backend.admin.dto.HouseholdDTO;
import com.backend.admin.dto.HouseholdRequest;
import com.backend.admin.model.auth.User;
import com.backend.admin.service.CustomLogger;
import com.backend.admin.service.HouseholdService;

@RestController
@RequestMapping(value = "/api/households", produces = MediaType.APPLICATION_JSON_VALUE)
public class HouseholdController {
    @Autowired
    private HouseholdService householdService;
    @Autowired
    private CustomLogger logger;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_HOUSEHOLDS')")
    public ResponseEntity<List<HouseholdDTO>> loadAll(@RequestParam(required = false) boolean detailed) {
        logger.info("all households");
        return new ResponseEntity<>(
                householdService.getAll().stream().map((h) -> new HouseholdDTO(h, detailed)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_HOUSEHOLDS')")
    public ResponseEntity<HouseholdDTO> getById(@PathVariable ObjectId id) {
        return householdService.findById(id)
                .map(value -> new ResponseEntity<>(new HouseholdDTO(value, true), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("byUser/{id}")
    @PreAuthorize("hasAuthority('READ_OWN_HOUSEHOLDS')")
    public ResponseEntity<HouseholdDTO> getByIdAndUser(@PathVariable ObjectId id, @AuthenticationPrincipal User user) {
        return householdService.findByIdAndUser(id, user)
                .map(value -> new ResponseEntity<>(new HouseholdDTO(value, true), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        
    }

    @GetMapping("byUser")
    @PreAuthorize("hasAuthority('READ_OWN_HOUSEHOLDS')")
    public ResponseEntity<List<HouseholdDTO>> getByUser(@AuthenticationPrincipal User user) {
        System.out.println(user.getUsername());
        return new ResponseEntity<>(
                householdService.getByUser(user).stream().map((h) -> new HouseholdDTO(h, false)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_HOUSEHOLD')")
    public ResponseEntity<HouseholdDTO> addHousehold(@Valid @RequestBody HouseholdRequest householdRequest) {
        return new ResponseEntity<>(new HouseholdDTO(householdService.create(householdRequest)), HttpStatus.CREATED);
    }

    @PostMapping("/device")
    @PreAuthorize("hasAuthority('CREATE_HOUSEHOLD')")
    public ResponseEntity<HouseholdDTO> addDevice(@Valid @RequestBody DeviceRequest deviceRequest)
            throws InvalidAlgorithmParameterException {
        return new ResponseEntity<>(new HouseholdDTO(householdService.addDevice(deviceRequest),true), HttpStatus.CREATED);
    }

    @DeleteMapping("/device/{houseId}/{deviceId}")
    @PreAuthorize("hasAuthority('CREATE_HOUSEHOLD')")
    public ResponseEntity<HouseholdDTO> removeDevice(@PathVariable ObjectId houseId, @PathVariable ObjectId deviceId) {
        return new ResponseEntity<>(new HouseholdDTO(householdService.removeDevice(houseId, deviceId),true), HttpStatus.OK);
    }
}
