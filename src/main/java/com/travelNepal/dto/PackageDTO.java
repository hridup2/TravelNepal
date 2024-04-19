package com.travelNepal.dto;

import java.util.List;

import com.travelNepal.entity.Feedback;
import com.travelNepal.entity.Hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageDTO {

    private Integer packageId;
    private String packageName;
    private String packageDescription;
    private Integer days;
    private Double packagePrice;
    private String mapScreenshotUrl;
    private String destinationPhotoUrl;
    private List<Hotel> hotels;
    private List<Feedback> feedbacks;
}