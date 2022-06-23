package com.backend.myhouse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.myhouse.dto.HouseholdMessagesDTO;
import com.backend.myhouse.services.MessageService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('READ_MESSAGES')")
    public ResponseEntity<List<HouseholdMessagesDTO>> getAll(Authentication authentication) {
        return new ResponseEntity<List<HouseholdMessagesDTO>>(
                messageService.findAllForHouseholds(authentication.getName()), HttpStatus.OK);
    }

}
