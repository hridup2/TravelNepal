package com.travelNepal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.*;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.dto.BookingDTO;
import com.travelNepal.entity.CurrentUserSession.Role;
import com.travelNepal.entity.PaymentDetails.PaymentType;
import com.travelNepal.repository.BookingRepository;
import com.travelNepal.repository.PackageRepository;
import com.travelNepal.repository.SessionRepository;
import com.travelNepal.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService {
	
	// Autowired annotation for the BookingRepository dependency
	@Autowired
	private BookingRepository bookRepo;
	
	// Autowired annotation for the SessionRepository dependency
	@Autowired
	private SessionRepository sessRepo;

	// Autowired annotation for the CustomerRepository dependency
	@Autowired
	private UserRepository userRepo;

	// Autowired annotation for the PackageRepository dependency
	@Autowired
	private PackageRepository packageRepo;

	@Override
	public Booking makeBooking(String sessionId, BookingDTO bookingdto) throws BookingException, LoginException,
			UsersException, PackageException {

		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		Optional<Users> user = userRepo.findById(cus.getUserId());
		if (user.isEmpty())
			throw new UsersException("customer not found");
		Users us = user.get();

		Booking newBooking = new Booking();
		newBooking.setBookingDate(LocalDateTime.now());
		newBooking.setBookingTitle(bookingdto.getBookingTitle());
		newBooking.setDescription(bookingdto.getDescription());
		newBooking.setNumberOfPeople(bookingdto.getNumberOfPeople());

		Optional<Package> pac = packageRepo.findById(bookingdto.getPackage_id());
		if (pac.isEmpty())
			throw new PackageException("package not found");
		Package packages = pac.get();
		packages.getBookings().add(newBooking);
		newBooking.setTourPackage(packages);

		PaymentDetails pm = new PaymentDetails();
		PaymentType payType = PaymentType.valueOf((bookingdto.getPaymentType()).toUpperCase());
		pm.setPaymentType(payType);
		pm.setPaymentDate(LocalDateTime.now());
		pm.setAmount(bookingdto.getAmount());
		pm.setBooking(newBooking);
		newBooking.setPaymentDetails(pm);

		return bookRepo.save(newBooking);
	}
	

	@Override
	public Booking cancelBooking(String sessionId, Integer bookingId) throws LoginException, BookingException, UsersException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		Optional<Users> us = userRepo.findById(cus.getUserId());
		if (us.isEmpty())
			throw new UsersException("booking not found");
		Optional<Booking> booking = bookRepo.findById(bookingId);
		if (booking.isEmpty())
			throw new BookingException("booking not found");
		Booking cancelBook = booking.get();

		return bookRepo.save(cancelBook);
	}
	

	@Override
	public Booking viewBooking(String sessionId, Integer bookingId) throws BookingException, LoginException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		Optional<Booking> booking = bookRepo.findById(bookingId);
		if (booking.isEmpty())
			throw new BookingException("booking not found");
		return booking.get();
	}

	@Override
	public List<Booking> viewAllBooking(String sessionId) throws BookingException, LoginException, AdminException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);

		if (cus == null)
			throw new LoginException("you're not logged in");
		if (cus.getRole() == Role.ADMIN) {
			List<Booking> bookings = bookRepo.findAll();
			if (bookings.isEmpty())
				throw new BookingException("no bookings");
			return bookings;
		}
		throw new AdminException("User not Authorized!");
	}

}