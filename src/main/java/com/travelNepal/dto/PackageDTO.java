package com.travelNepal.dto;

import java.util.List;

import com.travelNepal.entity.Feedback;

import com.travelNepal.entity.PackageDescription;
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
    private PackageDescription packageDescription;
    private Integer days;
    private Double packagePrice;
    private String mapPhotoUrl;
    private String destinationPhotoUrl;
    private List<Feedback> feedbacks;
}