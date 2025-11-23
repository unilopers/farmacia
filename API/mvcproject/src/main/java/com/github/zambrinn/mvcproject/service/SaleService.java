package com.github.zambrinn.mvcproject.service;

import com.github.zambrinn.mvcproject.DTOs.SaleRequest;
import com.github.zambrinn.mvcproject.DTOs.SaleResponse;
import com.github.zambrinn.mvcproject.model.*;
import com.github.zambrinn.mvcproject.repository.CustomerRepository;
import com.github.zambrinn.mvcproject.repository.ProductRepository;
import com.github.zambrinn.mvcproject.repository.SaleRepository;
import com.github.zambrinn.mvcproject.repository.UserTableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public SaleResponse createSale(SaleRequest request) {
        Customer customer = null;
        if (request.customerId() != null) {
            customer = customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id " + request.customerId()));
        }

        UserTable seller = userTableRepository.findById(request.sellerId())
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with id " + request.sellerId()));

        Sale sale = Sale.builder()
                .saleDate(LocalDateTime.now())
                .customer(customer)
                .seller(seller)
                .totalAmount(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleRequest.SaleItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id " + itemRequest.productId()));

            if (product.getStockquantity() < itemRequest.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for product " + product.getProductName());
            }

            SaleItem saleItem = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .unitPrice(product.getSellPrice())
                    .build();

            sale.getItems().add(saleItem);

            BigDecimal subtotal = product.getSellPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            totalAmount = totalAmount.add(subtotal);

            product.setStockquantity(product.getStockquantity() - itemRequest.quantity());
            productRepository.save(product);
        }

        sale.setTotalAmount(totalAmount);
        Sale savedSale = saleRepository.save(sale);

        return convertToDTO(savedSale);
    }

    public List<SaleResponse> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SaleResponse updateSale(UUID id, SaleRequest request) {
        Sale foundSale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found with id " + id));

        Customer customer = null;
        if (request.customerId() != null) {
            customer = customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id " + request.customerId()));
        }

        UserTable seller = userTableRepository.findById(request.sellerId())
                .orElseThrow(() -> new IllegalArgumentException("Seller not found with id " + request.sellerId()));

        for (SaleItem oldItem : foundSale.getItems()) {
            Product product = oldItem.getProduct();
            product.setStockquantity(product.getStockquantity() + oldItem.getQuantity());
            productRepository.save(product);
        }

        foundSale.getItems().clear();

        foundSale.setCustomer(customer);
        foundSale.setSeller(seller);
        foundSale.setSaleDate(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleRequest.SaleItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id " + itemRequest.productId()));

            if (product.getStockquantity() < itemRequest.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for product " + product.getProductName());
            }

            SaleItem saleItem = SaleItem.builder()
                    .sale(foundSale)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .unitPrice(product.getSellPrice())
                    .build();

            foundSale.getItems().add(saleItem);

            BigDecimal subtotal = product.getSellPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            totalAmount = totalAmount.add(subtotal);

            product.setStockquantity(product.getStockquantity() - itemRequest.quantity());
            productRepository.save(product);
        }

        foundSale.setTotalAmount(totalAmount);
        Sale updatedSale = saleRepository.save(foundSale);

        return convertToDTO(updatedSale);
    }

    public void delete(UUID id) {
        Sale foundSale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found with id " + id));

        saleRepository.deleteById(id);
    }

    public SaleResponse convertToDTO(Sale sale) {
        List<SaleResponse.SaleItemDTO> itemDTOs = sale.getItems().stream()
                .map(item -> new SaleResponse.SaleItemDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice()
                ))
                .toList();

        return new SaleResponse(
                sale.getId(),
                sale.getCustomer() != null ? sale.getCustomer().getId() : null,
                sale.getSeller().getId(),
                itemDTOs,
                sale.getSaleDate(),
                sale.getTotalAmount()
                );
    }
}
