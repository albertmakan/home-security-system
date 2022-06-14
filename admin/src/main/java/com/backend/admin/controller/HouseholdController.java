package com.backend.admin.controller;

import com.backend.admin.dto.HouseholdDTO;
import com.backend.admin.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/households", produces = MediaType.APPLICATION_JSON_VALUE)
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_HOUSEHOLDS')")
    public ResponseEntity<List<HouseholdDTO>> loadAll(@RequestParam(required = false) boolean detailed) {
        return new ResponseEntity<>(
                householdService.getAll().stream().map((h) -> new HouseholdDTO(h, detailed)).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
