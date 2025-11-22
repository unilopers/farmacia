package com.github.zambrinn.mvcproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.zambrinn.mvcproject.model.SaleItem;
import java.util.UUID;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, UUID> {
}