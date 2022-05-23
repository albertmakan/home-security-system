package com.backend.admin.controller;

import com.backend.admin.model.Household;
import com.backend.admin.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/households", produces = MediaType.APPLICATION_JSON_VALUE)
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_HOUSEHOLDS')")
    public List<Household> loadAll() {
        return householdService.getAll();
    }
}
