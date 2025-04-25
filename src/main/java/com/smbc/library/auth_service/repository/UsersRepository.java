package com.smbc.library.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smbc.library.auth_service.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findOneByUsername(String username);

    Users findOneByUsernameAndPassword(String username, String password);
}
