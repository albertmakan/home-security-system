package com.backend.admin.controller;

import com.backend.admin.dto.ManageUsersHouseholdsRequest;
import com.backend.admin.dto.auth.ChangePasswordRequest;
import com.backend.admin.dto.auth.ChangeRoleRequest;
import com.backend.admin.dto.auth.UserRequest;
import com.backend.admin.model.auth.User;
import com.backend.admin.service.auth.UserService;
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
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('REGISTER_USERS')")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/change-role")
    @PreAuthorize("hasAuthority('CHANGE_ROLE')")
    public ResponseEntity<User> changeRole(@Valid @RequestBody ChangeRoleRequest changeRoleRequest) {
        return new ResponseEntity<>(userService.changeRole(changeRoleRequest), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public List<User> loadAll() {
        return userService.findAll();
    }

    @PostMapping("/manage-households")
    @PreAuthorize("hasAuthority('MANAGE_USERS_HOUSEHOLDS')")
    public ResponseEntity<User> manageHouseholds(@Valid @RequestBody ManageUsersHouseholdsRequest request) {
        return new ResponseEntity<>(userService.manageHouseholds(request), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAuthority('CHANGE_PASSWORD')")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        boolean success = userService.changePassword(request);
        return new ResponseEntity<>(success? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable ObjectId id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
