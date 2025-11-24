package com.github.zambrinn.mvcproject.controller;

import com.github.zambrinn.mvcproject.DTOs.SaleItemRequest;
import com.github.zambrinn.mvcproject.DTOs.SaleItemResponse;
import com.github.zambrinn.mvcproject.service.SaleItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales/{saleId}/items") 
public class SaleItemController {

    @Autowired
    private SaleItemService saleItemService;

    @GetMapping     
    public ResponseEntity<List<SaleItemResponse>> getSaleItems(@PathVariable UUID saleId) {
        return ResponseEntity.ok(saleItemService.getSaleItems(saleId));
    }
    @PostMapping 
    public ResponseEntity<SaleItemResponse> addItemToSale(
            @PathVariable UUID saleId,
            @Valid @RequestBody SaleItemRequest request) {
        return ResponseEntity.ok(saleItemService.createSaleItem(saleId, request));
    }

    @PutMapping("/{itemId}") 
    public ResponseEntity<SaleItemResponse> updateItemInSale(
            @PathVariable UUID saleId, 
            @PathVariable UUID itemId,
            @Valid @RequestBody SaleItemRequest request) {
        return ResponseEntity.ok(saleItemService.updateSaleItem(saleId, itemId, request));
    }

    @DeleteMapping("/{itemId}") 
    public ResponseEntity<Void> removeItemFromSale(
            @PathVariable UUID saleId,
            @PathVariable UUID itemId) {
        saleItemService.removeItemFromSale(saleId, itemId);
        return ResponseEntity.noContent().build();
    }
}