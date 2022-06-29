package com.backend.admin.controller;

import com.backend.admin.model.rules.AlarmRule;
import com.backend.admin.service.AlarmRuleService;
import com.backend.admin.service.CustomLogger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/alarm-rules", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmRuleController {
    @Autowired
    private AlarmRuleService alarmRuleService;
    @Autowired
    private CustomLogger logger;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_ALARM_RULES')")
    public ResponseEntity<List<AlarmRule>> loadAll() {
        return new ResponseEntity<>(alarmRuleService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_ALARM_RULES')")
    public ResponseEntity<AlarmRule> create(@Valid @RequestBody AlarmRule alarmRule) {
        return new ResponseEntity<>(alarmRuleService.create(alarmRule), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ALARM_RULES')")
    public ResponseEntity<Void> remove(@PathVariable ObjectId id) {
        alarmRuleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
