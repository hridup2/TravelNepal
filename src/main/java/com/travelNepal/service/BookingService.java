package com.travelNepal.service;

import com.travelNepal.dto.BookingDTO;
import com.travelNepal.entity.Booking;
import com.travelNepal.exception.*;

import java.util.List;

public interface BookingService {


    Booking makeBooking(String uuid, BookingDTO bookingdto) throws BookingException, LoginException, UsersException, PackageException;

    Booking cancelBooking(String sessionId, Integer bookingId) throws BookingException, LoginException, UsersException;

    List<Booking> viewBooking(String sessionId, Integer bookingId) throws BookingException, LoginException;

    List<Booking> viewAllBooking(String sessionId) throws BookingException, LoginException, AdminException;


}