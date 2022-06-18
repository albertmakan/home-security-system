package com.backend.admin.controller;

import com.backend.admin.model.Logs;
import com.backend.admin.service.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogsController {
    @Autowired
    private CustomLogger logger;

    @GetMapping("/all")
    public ResponseEntity<List<Logs>> loadAll() {
        return new ResponseEntity<>(logger.findAll(),HttpStatus.OK);
    }
}
