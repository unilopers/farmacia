package com.github.zambrinn.mvcproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate = LocalDateTime.now();

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private UserTable seller;

    @ManyToOne
    @JoinColumn(name = "saleItem_id", nullable = false)
    private SaleItem saleItem;

}