package com.backend.myhouse.controller;

import com.backend.myhouse.model.Household;
import com.backend.myhouse.model.auth.User;
import com.backend.myhouse.services.CustomLogger;
import com.backend.myhouse.services.HouseholdService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/households", produces = MediaType.APPLICATION_JSON_VALUE)
public class HouseholdController {
    @Autowired
    private HouseholdService householdService;
    @Autowired
    private CustomLogger logger;

    @GetMapping("/my/{id}")
    @PreAuthorize("hasAuthority('READ_MY_HOUSEHOLDS')")
    public ResponseEntity<Household> getById(@PathVariable ObjectId id, @AuthenticationPrincipal User user) {
        return user.getHouseholds().stream().filter(h -> h.getId().equals(id)).findAny()
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.FORBIDDEN));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('READ_MY_HOUSEHOLDS')")
    public ResponseEntity<List<Household>> getMy(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(user.getHouseholds(), HttpStatus.OK);
    }
}
