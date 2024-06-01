package com.travelNepal.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.*;
import com.travelNepal.entity.Package;
import com.travelNepal.enums.BookingStatus;
import com.travelNepal.exception.*;
import com.travelNepal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.dto.BookingDTO;
import com.travelNepal.entity.CurrentUserSession.Role;

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

	@Autowired
	private HotelRepository hotelRepo;

	@Override
	public Booking makeBooking(String sessionId, BookingDTO bookingdto) throws BookingException, LoginException,
			UsersException, PackageException, HotelException {

		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		Optional<Users> user = userRepo.findById(cus.getUserId());
		if (user.isEmpty())
			throw new UsersException("customer not found");
		Users us = user.get();

		Booking newBooking = new Booking();
		newBooking.setStartDateJourney(LocalDate.from(LocalDateTime.now()));
		newBooking.setGuestEmail(bookingdto.getGuestEmail());
		newBooking.setNumberOfGuest(bookingdto.getNumberOfGuest());
		newBooking.setBookingTittle(bookingdto.getBookingTittle());
		newBooking.setDescription(bookingdto.getDescription());
		newBooking.setUsers(us);
        newBooking.setBookingStatus(BookingStatus.BOOKED);

		Optional<Hotel> hotelopt = hotelRepo.findById(bookingdto.getHotel_id());
		if (hotelopt.isEmpty())
			throw new HotelException("hotel not available");
		Hotel hotel = hotelopt.get();
		hotel.getBookings().add(newBooking);
		newBooking.setHotel(hotel);

		Optional<Package> pac = packageRepo.findById(bookingdto.getPackage_id());
		if (pac.isEmpty())
			throw new PackageException("package not found");
		Package packages = pac.get();
		packages.getBookings().add(newBooking);
		newBooking.setTourPackage(packages);

		return bookRepo.save(newBooking);
	}


	@Override
	public Booking cancelBooking(String sessionId, Integer bookingId) throws LoginException, BookingException, UsersException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		Optional<Users> us = userRepo.findById(cus.getUserId());
		if (us.isEmpty())
			throw new UsersException("user not found");
		Optional<Booking> booking = bookRepo.findById(bookingId);
		if (booking.isEmpty())
			throw new BookingException("booking not found");
		Booking cancelBook = booking.get();
		cancelBook.setBookingStatus(BookingStatus.CANCELED);
		return bookRepo.save(cancelBook);
	}


	@Override
	public List<Booking> viewBooking(String sessionId, Integer userId) throws BookingException, LoginException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		Optional<List<Booking>> booking = bookRepo.findByUsers_userIdAndBookingStatus(userId,BookingStatus.BOOKED);
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