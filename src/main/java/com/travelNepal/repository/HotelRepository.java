package com.travelNepal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelNepal.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

	public List<Hotel> findByHotelName(String hotelName);
}