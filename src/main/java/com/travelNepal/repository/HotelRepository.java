package com.travelNepal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelNepal.entity.Hotel;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query(value = "SELECT * FROM hotel WHERE hotel_status = 'DELETED'", nativeQuery = true)
    public List<Hotel> findAllHotel();
}