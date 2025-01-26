package com.travelNepal.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import com.travelNepal.enums.HotelStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId;
    private String hotelPhotoUrl;
    private String hotelName;
    private String email;
    private String hotelDescription;
    private Double hotelRent;
    private String hotelAddress;

    private HotelStatus hotelStatus;

    public Hotel(Integer hotelId, String hotelPhotoUrl, String hotelName, String email, String hotelDescription,
                 Double hotelRent, String hotelAddress) {
        super();
        this.hotelId = hotelId;
        this.hotelPhotoUrl = hotelPhotoUrl;
        this.hotelName = hotelName;
        this.email = email;
        this.hotelDescription = hotelDescription;
        this.hotelRent = hotelRent;
        this.hotelAddress = hotelAddress;
    }

    //Relationships
    @JsonIgnore
    @ManyToMany(mappedBy = "hotels")
    private List<Package> packages = new ArrayList<>();

    // Relationship mappings
    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private List<Booking> bookings= new ArrayList<>();

    @Override
    public String toString() {
        return "Hotel [hotelId=" + hotelId + ", hotelPhoto=" + hotelPhotoUrl + ", hotelName=" + hotelName + ", email=" + email + ", hotelDescription="
                + hotelDescription + ", hotelRent=" + hotelRent + ", hotelAddress="
                + hotelAddress + "]";
    }



}