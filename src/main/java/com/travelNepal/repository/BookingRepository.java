package com.travelNepal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelNepal.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}