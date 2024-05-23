package com.travelNepal.service;

import java.util.List;

import com.travelNepal.dto.FeedbackResponse;
import com.travelNepal.entity.Feedback;
import com.travelNepal.exception.FeedbackException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.exception.UsersException;

public interface FeedbackService {
	public FeedbackResponse addFeedback(String sessionId, Feedback feedback, Integer packageId) throws LoginException, FeedbackException, UsersException;
	public List<Feedback> getFeedbackByPackageId(String sessionId, int packageid) throws LoginException, PackageException,  FeedbackException;
	public FeedbackResponse updateFeedback(Feedback feedback, String sessionId, Integer feedbackId) throws LoginException, UsersException, FeedbackException;
	public void deleteFeedback(String sessionId, Integer feedbackId) throws LoginException, UsersException, FeedbackException;
}
