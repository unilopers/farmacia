package com.github.zambrinn.mvcproject.model.repository;

import com.github.zambrinn.mvcproject.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserTableRepository extends JpaRepository<UserTable, UUID> {
}
