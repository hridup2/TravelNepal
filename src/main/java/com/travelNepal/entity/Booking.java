package com.travelNepal.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookingId;
	private String bookingTitle;
	private String description;
	private Integer numberOfPeople;
	private String country;
	private LocalDateTime bookingDate;
	
	//relationships
	
	// Many-to-one relationship with Customer
	@JsonProperty(access = Access.READ_ONLY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Users users;
	
    // Many-to-one relationship with Package
	@JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "packageId")
    private Package tourPackage;

    
    // One-to-one relationship with PaymentDetails
	@JsonProperty(access = Access.READ_ONLY)
    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL)
    private PaymentDetails paymentDetails;

}
