package com.travelNepal.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingDTO {

	private Integer bookingId;
	private String bookingTittle;
	private String guestEmail;
	private Integer numberOfGuest;
	private String startDateJourney;
	private String description;
	private Integer user_id;
	private Integer package_id;
	
}