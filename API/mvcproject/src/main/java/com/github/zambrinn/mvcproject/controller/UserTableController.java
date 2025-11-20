package com.github.zambrinn.mvcproject.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zambrinn.mvcproject.DTOs.UserTableRequest;
import com.github.zambrinn.mvcproject.DTOs.UserTableResponse;
import com.github.zambrinn.mvcproject.service.UserTableService;

import jakarta.validation.Valid;

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
