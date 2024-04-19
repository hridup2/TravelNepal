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
import com.travelNepal.repository.HotelRepository;
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

	// Autowired annotation for the HotelRepository dependency
	@Autowired
	private HotelRepository hotelRepo;

	// Autowired annotation for the PackageRepository dependency
	@Autowired
	private PackageRepository packageRepo;

	// Autowired annotation for the EmailsenderService dependency
	@Autowired
	private EmailsenderService emailService;
	
	/**
	 * Makes a booking based on the provided booking details.
	 *
	 * @param sessionId    The session ID of the current user.
	 * @param bookingdto   The booking details.
	 * @return The created booking.
	 * @throws BookingException     If there is an issue with the booking.
	 * @throws LoginException       If the user is not logged in.
	 * @throws UsersException        If the customer is not found.
	 * @throws RouteException       If the route is not found.
	 * @throws PackageException     If the package is not found.
	 * @throws HotelException       If the hotel is not available.
	 */
	@Override
	public Booking makeBooking(String sessionId, BookingDTO bookingdto) throws BookingException, LoginException,
			UsersException, RouteException, PackageException, HotelException {

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

		PaymentDetails pm = new PaymentDetails();
		PaymentType payType = PaymentType.valueOf((bookingdto.getPaymentType()).toUpperCase());
		pm.setPaymentType(payType);
		pm.setPaymentDate(LocalDateTime.now());
		pm.setAmount(bookingdto.getAmount());
		pm.setBooking(newBooking);
		newBooking.setPaymentDetails(pm);

		// Send an email to the customer to notify them about the confirmation of booking
		String emailSubject = "Booking Confirmation "+bookingdto.getBookingTitle();
		String emailBody = "Dear " + us.getName() + ",\n\n" +
		        "We are pleased to inform you that your booking has been successfully confirmed. Below are the details of your booking:\n\n" +
		        "Booking Title: " + bookingdto.getBookingTitle() + "\n" +
		        "Description: " + bookingdto.getDescription() + "\n" +
		        "Number of People: " + bookingdto.getNumberOfPeople() + "\n\n" +
		        "We have assigned the following hotel to your package:\n\n" +
		        "Hotel Name: " + hotel.getHotelName() + "\n" +
		        "Hotel Address: " + hotel.getHotelAddress() + "\n\n" +
		        "Please note that your payment of " + bookingdto.getAmount() + " has been received and processed.\n\n" +
		        "If you have any questions or require further assistance, please feel free to contact our customer support team at " +
		        "teamimperialtravel@gmail.com" + ".\n\n" +
		        "Thank you for choosing our services. We look forward to serving you.\n\n" +
		        "Best regards,\n" +
		        "[Team Imperial Travels]";
		
		emailService.sendEmail(us.getEmail(), emailSubject, emailBody);

		System.out.println("booking confirmed.....!");

		return bookRepo.save(newBooking);
	}
	
	
	/**
	 * Cancels a booking based on the provided booking ID.
	 *
	 * @param sessionId  The session ID of the current user.
	 * @param bookingId  The ID of the booking to be canceled.
	 * @return The canceled booking.
	 * @throws LoginException      If the user is not logged in.
	 * @throws BookingException    If the booking is not found.
	 * @throws UsersException   If the customer is not found.
	 */
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
		
		// Send an email to the customer to notify them about the confirmation of booking cancellation
				String emailSubject = "Booking Cancellation - Confirmation";
				String emailBody = "Dear " + us.get().getName() + ",\n\n" +
				        "We are pleased to inform you that your booking has been successfully cancelled. Below are the details of your booking:\n\n" +
				        "Booking Title: " + cancelBook.getBookingTitle() + "\n" +
				        "Description: " + cancelBook.getDescription() + "\n" +
				        "Number of People: " + cancelBook.getNumberOfPeople() + "\n\n" +
				        "The refund of " + cancelBook.getPaymentDetails().getAmount() + " has been processed and will be credited to your account within 7 days.\n\n" +
				        "If you have any questions or require further assistance, please feel free to contact our customer support team at " +
				        "teamimperialtravel@gmail.com" + ".\n\n" +
				        "Thank you for choosing our services. We look forward to serving you.\n\n" +
				        "Best regards,\n" +
				        "[Team Imperial Travels]";
//
//		if(cancelBook.getTicketDetails().getTicketStatus()==Status.CONFIRMED) {
//			cancelBook.getTicketDetails().setTicketStatus(Status.CANCELED);
//			emailService.sendEmail("rahulchamoli518@gmail.com", emailSubject, emailBody);
//		}
//		else {
//			throw new BookingException("this booking is already canceled");
//		}
		
		return bookRepo.save(cancelBook);
	}
	
	/**
	 * Retrieves a specific booking based on the provided booking ID.
	 *
	 * @param sessionId  The session ID of the user.
	 * @param bookingId  The ID of the booking to retrieve.
	 * @return The booking matching the provided ID.
	 * @throws BookingException if the booking is not found.
	 * @throws LoginException   if the user is not logged in.
	 */
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
	
	/**
	 * Retrieves all bookings.
	 *
	 * @param sessionId The session ID of the user.
	 * @return The list of all bookings.
	 * @throws BookingException if there are no bookings available.
	 * @throws LoginException   if the user is not logged in.
	 * @throws AdminException   if the user is not authorized as an admin.
	 */
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