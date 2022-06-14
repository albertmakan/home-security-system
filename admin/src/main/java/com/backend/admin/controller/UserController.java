package com.backend.admin.controller;

import com.backend.admin.dto.ManageUsersHouseholdsRequest;
import com.backend.admin.dto.UserDTO;
import com.backend.admin.dto.auth.ChangePasswordRequest;
import com.backend.admin.dto.auth.ChangeRoleRequest;
import com.backend.admin.dto.auth.UserRequest;
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
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('REGISTER_USERS')")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(new UserDTO(userService.create(userRequest)), HttpStatus.CREATED);
    }

    @PostMapping("/change-role")
    @PreAuthorize("hasAuthority('CHANGE_ROLE')")
    public ResponseEntity<UserDTO> changeRole(@Valid @RequestBody ChangeRoleRequest changeRoleRequest) {
        return new ResponseEntity<>(new UserDTO(userService.changeRole(changeRoleRequest)), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public ResponseEntity<List<UserDTO>> loadAll(@RequestParam(required = false) boolean detailed) {
        return new ResponseEntity<>(
                userService.findAll().stream().map(u -> new UserDTO(u, detailed)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping("/manage-households")
    @PreAuthorize("hasAuthority('MANAGE_USERS_HOUSEHOLDS')")
    public ResponseEntity<UserDTO> manageHouseholds(@Valid @RequestBody ManageUsersHouseholdsRequest request) {
        return new ResponseEntity<>(new UserDTO(userService.manageHouseholds(request)), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAuthority('CHANGE_PASSWORD')")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        boolean success = userService.changePassword(request);
        return new ResponseEntity<>(success? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<Void> deleteUser(@PathVariable ObjectId id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
