package com.travelNepal.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

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



    // One-to-many relationship with Booking
    @JsonIgnore
    @OneToMany(mappedBy = "tourPackage")
    private List<Booking> bookings = new ArrayList<>();


    @ElementCollection
    @Embedded
    @JoinTable(
            name = "feedback",
            joinColumns = @JoinColumn(name = "packageId")
    )
    private List<Feedback> feedbacks = new ArrayList<>();



}