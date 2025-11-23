package com.github.zambrinn.mvcproject.controller;

import com.github.zambrinn.mvcproject.DTOs.SaleResponse;
import com.github.zambrinn.mvcproject.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<SaleResponse> getSale() {
        return ResponseEntity.ok(SaleService.getAllSale());
    }
}
