package com.travelNepal.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackId;
	private String name;
	private String feedback;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime submitTime = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "packageId", nullable = false,referencedColumnName = "packageId")
	private Package pack;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false,referencedColumnName = "userId")
	private Users user;

}
