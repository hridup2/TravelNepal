package com.travelNepal.repository;

import com.travelNepal.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.travelNepal.entity.Booking;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {


    Optional<List<Booking>> findByUsers_userIdAndBookingStatus(Integer id, BookingStatus bookingStatus);
}