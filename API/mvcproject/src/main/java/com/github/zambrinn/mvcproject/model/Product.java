package com.github.zambrinn.mvcproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "sell_price", nullable = false)
    private BigDecimal sellPrice;

    @Column(name = "category")
    private String category;

    @Column(name = "stock_quantity", nullable = false)
    private int stockquantity = 0;

    public int getStockquantity() {
        if (stockquantity < 0) throw new IllegalArgumentException("The stock quantity cannot be negative");
        return stockquantity;
    }

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
