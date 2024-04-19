package com.travelNepal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelNepal.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    public Users findByEmail(String email);
}