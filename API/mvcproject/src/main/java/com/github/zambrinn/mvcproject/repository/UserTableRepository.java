package com.github.zambrinn.mvcproject.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.zambrinn.mvcproject.model.UserTable;

@Repository
public interface UserTableRepository extends JpaRepository<UserTable, UUID> {
}
