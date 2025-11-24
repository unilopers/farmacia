package com.github.zambrinn.mvcproject.controller;

import com.github.zambrinn.mvcproject.DTOs.SaleRequest;
import com.github.zambrinn.mvcproject.DTOs.SaleResponse;
import com.github.zambrinn.mvcproject.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest saleRequest) {
        return ResponseEntity.ok(saleService.createSale(saleRequest));
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponse> updateSale(@PathVariable UUID id, @RequestBody SaleRequest saleRequest) {
        return ResponseEntity.ok(saleService.updateSale(id, saleRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable UUID id) {
        saleService.delete(id);
    }
}
