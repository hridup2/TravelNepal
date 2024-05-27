package com.travelNepal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Map;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageDescription {

    private String mapPhotoUrl;

    @ElementCollection
    @CollectionTable(name = "map_itinerary", joinColumns = @JoinColumn(name = "package_id"))
    @MapKeyColumn(name = "day")
    @Column(name = "description")
    private Map<String, String> mapItinerary;

    private String safetyGuidelines;


}
