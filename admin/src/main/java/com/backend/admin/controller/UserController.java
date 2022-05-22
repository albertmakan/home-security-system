package com.backend.admin.controller;

import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.model.auth.User;
import com.backend.admin.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public List<User> loadAll() {
        return userService.findAll();
    }
}
