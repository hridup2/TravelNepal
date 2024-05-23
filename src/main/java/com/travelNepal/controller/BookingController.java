package com.travelNepal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travelNepal.dto.BookingDTO;
import com.travelNepal.entity.Booking;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.BookingException;
import com.travelNepal.exception.UsersException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
@CrossOrigin("*")
public class BookingController {
	
	@Autowired
	private BookingService bookService;
	

	@PostMapping("/make/{sessionId}")
	public ResponseEntity<Booking> addBookingController(@Valid @RequestBody BookingDTO bookingdto, @PathVariable String sessionId) throws LoginException, BookingException, UsersException, PackageException{
		Booking book = bookService.makeBooking(sessionId, bookingdto);
		return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
	}
	

	@PutMapping("/cancel/{sessionId}")
	public ResponseEntity<Booking> cancelBookingController(@Valid  @PathVariable String sessionId,@RequestParam("bookingId") Integer bookingId) throws LoginException,BookingException, UsersException, PackageException{
		Booking book = bookService.cancelBooking(sessionId, bookingId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@GetMapping("/view/{sessionId}")
	public ResponseEntity<Booking> viewBookingController(@Valid  @PathVariable String sessionId,@RequestParam("bookingId") Integer bookingId) throws LoginException, BookingException, UsersException, PackageException{
		Booking book = bookService.viewBooking(sessionId, bookingId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	

	@GetMapping("/viewall/{sessionId}")
	public ResponseEntity<List<Booking>> viewAllBookingController(@Valid  @PathVariable String sessionId) throws LoginException, BookingException, UsersException, PackageException, AdminException{
		List<Booking> bookings = bookService.viewAllBooking(sessionId);
		return new ResponseEntity<>(bookings, HttpStatus.OK);
	}
	
}