package com.travelNepal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelNepal.enums.PackageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer packageId;
    private String packageName;

    @Embedded
    private PackageDescription packageDescription;

    private Integer days;
    private Double packagePrice;
    private String destinationPhotoUrl;

    private PackageStatus packageStatus;

    // One-to-many relationship with Booking
    @JsonIgnore
    @OneToMany(mappedBy = "tourPackage")
    private List<Booking> bookings = new ArrayList<>();

    // Relationship mappings
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "package_hotel",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    private List<Hotel> hotels = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();
}