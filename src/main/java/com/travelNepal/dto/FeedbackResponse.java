package com.travelNepal.dto;

import java.time.LocalDateTime;

import com.travelNepal.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FeedbackResponse {
	private LocalDateTime responseTime;
	private String message;
	private Feedback feedback;

	public FeedbackResponse(Feedback feedback) {
		this.responseTime = LocalDateTime.now();
		this.message = "Feedback submitted successfully!";
		this.feedback = feedback;
	}
}
