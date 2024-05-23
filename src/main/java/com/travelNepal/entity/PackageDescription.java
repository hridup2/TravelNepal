package com.travelNepal.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageDescription {

    private String mapPhotoUrl;
    private String mapItinerary;
    private String safetyGuidelines;
}
