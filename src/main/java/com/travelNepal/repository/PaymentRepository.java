package com.travelNepal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelNepal.entity.PaymentDetails;

public interface PaymentRepository extends JpaRepository<PaymentDetails, Integer>{

}
