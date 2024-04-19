package com.travelNepal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelNepal.dto.BookingDTO;
import com.travelNepal.entity.Booking;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.BookingException;
import com.travelNepal.exception.UsersException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.exception.RouteException;
import com.travelNepal.service.BookingService;

import jakarta.validation.Valid;

@RestController("/bookings")
@CrossOrigin("*")
public class BookingController {
	
	@Autowired
	private BookingService bookService;
	
	/**
	 * @param bookingdto  The booking details.
	 * @param sessionId   The session ID of the user.
	 * @return            The created booking.
	 */
	@PostMapping("/make/{sessionId}")
	public ResponseEntity<Booking> addBookingController(@Valid @RequestBody BookingDTO bookingdto, @PathVariable String sessionId) throws LoginException, BookingException, UsersException, RouteException, PackageException, HotelException{
		Booking book = bookService.makeBooking(sessionId, bookingdto);
		return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
	}
	
	/**
	 * @param sessionId   The session ID of the user.
	 * @param bookingId   The ID of the booking to cancel.
	 * @return            The canceled booking.	
	 */
	@PutMapping("/cancel/{sessionId}")
	public ResponseEntity<Booking> cancelBookingController(@Valid  @PathVariable String sessionId,@RequestParam("bookingId") Integer bookingId) throws LoginException,BookingException, UsersException, RouteException, PackageException{
		Booking book = bookService.cancelBooking(sessionId, bookingId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	/**
	 * @param sessionId   The session ID of the user.
	 * @param bookingId   The ID of the booking to view.
	 * @return            The viewed booking.
	 */
	@GetMapping("/view/{sessionId}")
	public ResponseEntity<Booking> viewBookingController(@Valid  @PathVariable String sessionId,@RequestParam("bookingId") Integer bookingId) throws LoginException, BookingException, UsersException, RouteException, PackageException{
		Booking book = bookService.viewBooking(sessionId, bookingId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	/**
	 * @param sessionId   The session ID of the user.
	 * @return            The list of all bookings.
	 */
	@GetMapping("/viewall/{sessionId}")
	public ResponseEntity<List<Booking>> viewAllBookingController(@Valid  @PathVariable String sessionId) throws LoginException, BookingException, UsersException, RouteException, PackageException, AdminException{
		List<Booking> bookings = bookService.viewAllBooking(sessionId);
		return new ResponseEntity<>(bookings, HttpStatus.OK);
	}
	
}