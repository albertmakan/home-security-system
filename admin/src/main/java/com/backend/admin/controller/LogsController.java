package com.backend.admin.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.admin.model.Log;
import com.backend.admin.service.CustomLogger;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogsController {
    @Autowired
    private CustomLogger logger;

    @GetMapping("/all")
    public ResponseEntity<List<Log>> loadAll() {
        return new ResponseEntity<>(logger.findAll(),HttpStatus.OK);
    }

    @GetMapping("/search-filter")
    public ResponseEntity<List<Log>> loadAllWithSearchAndFilter(String keyword, Boolean regexChosen, String level, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return new ResponseEntity<>(logger.loadAllWithSearchAndFilter(keyword, regexChosen, level, date),HttpStatus.OK);
    }
}
