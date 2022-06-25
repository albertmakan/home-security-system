package com.backend.myhouse.controller;

import com.backend.myhouse.dto.HouseholdMessagesDTO;
import com.backend.myhouse.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_MESSAGES')")
    public ResponseEntity<List<HouseholdMessagesDTO>> getAll(Authentication authentication, @RequestParam String filter,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return new ResponseEntity<>(messageService.findAllForHouseholds(authentication.getName(), filter, start, end), HttpStatus.OK);
    }

}
