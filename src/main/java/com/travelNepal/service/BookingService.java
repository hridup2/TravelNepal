package com.travelNepal.service;

import java.util.List;

import com.travelNepal.dto.BookingDTO;
import com.travelNepal.entity.Booking;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.BookingException;
import com.travelNepal.exception.UsersException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.exception.RouteException;

public interface BookingService {
	
	
	public Booking makeBooking(String uuid , BookingDTO bookingdto) throws BookingException,LoginException,UsersException, RouteException, PackageException,HotelException;
	
	public Booking cancelBooking(String sessionId, Integer bookingId) throws BookingException, LoginException, UsersException;
	
	public Booking viewBooking(String sessionId,Integer bookingId) throws BookingException, LoginException;
	
	public List<Booking> viewAllBooking(String sessionId) throws BookingException, LoginException, AdminException;
	

	
}