package com.travelNepal.controller;

import java.util.List;

import com.travelNepal.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travelNepal.dto.BookingDTO;
import com.travelNepal.entity.Booking;
import com.travelNepal.service.BookingService;
import java.util.Map;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
@CrossOrigin("*")
public class BookingController {
	
	@Autowired
	private BookingService bookService;
	

	@PostMapping("/make/{sessionId}")
	public ResponseEntity<Booking> addBookingController(@Valid @RequestBody BookingDTO bookingdto, @PathVariable String sessionId) throws LoginException, BookingException, UsersException, PackageException, HotelException {
		Booking book = bookService.makeBooking(sessionId, bookingdto);
		return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
	}
	

	@PutMapping("/cancel/{sessionId}")
	public ResponseEntity<Booking> cancelBookingController(@Valid  @PathVariable String sessionId,@RequestBody Map<String, Integer> request) throws LoginException,BookingException, UsersException, PackageException{
		Integer bookingId = request.get("bookingId");
		if (bookingId == null) {
			throw new IllegalArgumentException("Booking ID must be provided");
		}
		Booking book = bookService.cancelBooking(sessionId, bookingId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@GetMapping("/view/{sessionId}")
	public ResponseEntity<List<Booking>> viewBookingController(@Valid @PathVariable String sessionId,@RequestParam("userId") Integer userId) throws LoginException, BookingException, UsersException, PackageException{
		List<Booking> book = bookService.viewBooking(sessionId, userId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	

	@GetMapping("/viewall/{sessionId}")
	public ResponseEntity<List<Booking>> viewAllBookingController(@Valid  @PathVariable String sessionId) throws LoginException, BookingException, UsersException, PackageException, AdminException{
		List<Booking> bookings = bookService.viewAllBooking(sessionId);
		return new ResponseEntity<>(bookings, HttpStatus.OK);
	}

	@GetMapping("/from/{startDateJourney}")
	public List<Booking> getBookingsFromDate(@PathVariable String startDateJourney) {
		List<Booking> booking= bookService.getBookingsFromDate(startDateJourney);
		System.out.println("Bookings fetched: " + booking);
		return booking;
	}
	
}