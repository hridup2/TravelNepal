package com.travelNepal.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
	
	private Integer feedbackId;
	private String customerName;
	private String feedbackDesciption;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime submitTime = LocalDateTime.now();

}
