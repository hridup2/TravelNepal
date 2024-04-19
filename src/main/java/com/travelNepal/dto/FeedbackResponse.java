package com.travelNepal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackResponse {
	private LocalDateTime responseTime;
	private String message;
}
