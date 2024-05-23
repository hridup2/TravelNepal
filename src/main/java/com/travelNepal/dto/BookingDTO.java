package com.travelNepal.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class BookingDTO {
	
	private String bookingTitle;
	private String description;
	private Integer numberOfPeople;
	private LocalDate startDateJourney;
	private String country;
	private Integer user_id;
	private Integer package_id;
	private String paymentType;
	private double amount;
	
}