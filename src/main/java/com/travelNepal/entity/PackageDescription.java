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

    @ElementCollection
    @CollectionTable(name = "safety_guidelines", joinColumns = @JoinColumn(name = "package_id"))
    @MapKeyColumn(name = "guidelines_key")
    @Column(name = "description")
    private Map<String, String> safetyGuidelines;
}
