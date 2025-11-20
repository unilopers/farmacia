package com.github.zambrinn.mvcproject.controller;

import com.github.zambrinn.mvcproject.model.DTOs.UserTableRequest;
import com.github.zambrinn.mvcproject.model.DTOs.UserTableResponse;
import com.github.zambrinn.mvcproject.model.service.UserTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/UserTable")
@RestController
public class UserTableController {
    @Autowired
    private UserTableService userTableService;

    @GetMapping
    public ResponseEntity<List<UserTableResponse>> getAllUsers() {
        return ResponseEntity.ok(userTableService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserTableResponse> createUser(@Valid @RequestBody UserTableRequest request) {
        return ResponseEntity.ok(userTableService.createUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTableResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody UserTableRequest request) {
        return ResponseEntity.ok(userTableService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userTableService.deleteUser(id);
    }
}
