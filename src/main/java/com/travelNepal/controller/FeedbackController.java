package com.travelNepal.controller;

import java.util.List;
import com.travelNepal.exception.UsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travelNepal.dto.FeedbackResponse;
import com.travelNepal.entity.Feedback;
import com.travelNepal.exception.FeedbackException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.service.FeedbackService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedback")
@CrossOrigin("*")
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	
	@PostMapping("/addFeedback/{sessionId}/{packageId}")
	public ResponseEntity<FeedbackResponse> addFeedback(@Valid @RequestBody Feedback feedback, @PathVariable String sessionId, @PathVariable Integer packageId) throws LoginException, UsersException, FeedbackException{
		FeedbackResponse feed = feedbackService.addFeedback(sessionId, feedback, packageId);
		return new ResponseEntity<FeedbackResponse>(feed, HttpStatus.OK);
	}
	
	@GetMapping("/feedbacklist/{sessionId}/{packageId}")
	public ResponseEntity<List<Feedback>> getFeedbackByPackageId(@PathVariable String sessionId, @PathVariable int packageId) throws LoginException, PackageException, FeedbackException{
		List<Feedback> feed = feedbackService.getFeedbackByPackageId(sessionId, packageId);
		return new ResponseEntity<List<Feedback>>(feed, HttpStatus.OK);
	}

	@PutMapping("/updateFeedback/{sessionId}/{feedbackId}")
	public ResponseEntity<FeedbackResponse> updateFeedback(@Valid @RequestBody Feedback feedback, @PathVariable String sessionId, @PathVariable Integer feedbackId) throws LoginException, UsersException, FeedbackException {
		FeedbackResponse updatedFeedback = feedbackService.updateFeedback(feedback, sessionId, feedbackId);
		return new ResponseEntity<FeedbackResponse>(updatedFeedback, HttpStatus.OK);
	}

	@DeleteMapping("/deleteFeedback/{sessionId}/{feedbackId}")
	public ResponseEntity<Void> deleteFeedback(@PathVariable String sessionId, @PathVariable Integer feedbackId) throws LoginException, UsersException, FeedbackException {
		feedbackService.deleteFeedback(sessionId, feedbackId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
}