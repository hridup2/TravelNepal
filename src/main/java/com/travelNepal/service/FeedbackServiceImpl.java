package com.travelNepal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.Users;
import com.travelNepal.exception.UsersException;
import com.travelNepal.repository.FeedbackRepository;
import com.travelNepal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.dto.FeedbackResponse;
import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.CurrentUserSession.Role;
import com.travelNepal.entity.Feedback;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.FeedbackException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.repository.PackageRepository;
import com.travelNepal.repository.SessionRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	private SessionRepository sessRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PackageRepository packageRepo;
	@Autowired
	private FeedbackRepository feedbackRepo;


	@Override
	public FeedbackResponse addFeedback(String sessionId, Feedback feedback, Integer packageId) throws FeedbackException, LoginException, UsersException {
		// TODO Auto-generated method stub
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("You're not logged in");
		Optional<Users> user = userRepo.findById(cus.getUserId());
		if (user.isEmpty())
			throw new UsersException("Customer not found");

		Optional<Package> pack = packageRepo.findById(packageId);
		if (pack.isEmpty())
			throw new FeedbackException("Package not found with the provided id");


		feedback.setUser(user.get());
		feedback.setPack(pack.get());
		feedback.setSubmitTime(LocalDateTime.now());

		feedbackRepo.save(feedback);
		return new FeedbackResponse(LocalDateTime.now(), "Feedback sucessfully submitted");
	}

	@Override
	public List<Feedback> getFeedbackByPackageId(Integer packageId) throws FeedbackException, PackageException, LoginException{
		Optional<Package> pack = packageRepo.findById(packageId);
		if (!pack.isPresent()) {
			throw new PackageException("Package not found with the provided id");
		}
		List<Feedback> list = pack.get().getFeedbacks();
		if (list == null || list.isEmpty()) {
			throw new FeedbackException("NO feedback available ");
		}
		return list;
	}


	@Override
	public FeedbackResponse updateFeedback(Feedback feedback, String sessionId, Integer feedbackId) throws LoginException, UsersException, FeedbackException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null) {
			throw new LoginException("You're not logged in");
		}

		Optional<Feedback> optionalFeedback = feedbackRepo.findById(feedbackId);
		if (optionalFeedback.isEmpty()) {
			throw new FeedbackException("Feedback not found with id: " + feedbackId);
		}

		Feedback existingFeedback = optionalFeedback.get();

		if (!existingFeedback.getUser().getUserId().equals(cus.getUserId()) && cus.getRole() != Role.ADMIN) {
			throw new UsersException("You don't have permission to update this feedback");
		}

		// Step 4: Update the feedback with the new details
		existingFeedback.setName(feedback.getName());
		existingFeedback.setFeedback(feedback.getFeedback());
		Feedback updatedFeedback = feedbackRepo.save(existingFeedback);

		// Step 6: Convert the updated feedback to FeedbackResponse (assuming you have a conversion method)
		FeedbackResponse feedbackResponse = new FeedbackResponse(LocalDateTime.now(), "Feedback successfully updated");

		return feedbackResponse;
	}


	public void deleteFeedback(String sessionId, Integer feedbackId) throws LoginException, UsersException, FeedbackException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null) {
			throw new LoginException("You're not logged in");
		}

		Optional<Feedback> optionalFeedback = feedbackRepo.findById(feedbackId);
		if (optionalFeedback.isEmpty()) {
			throw new FeedbackException("Feedback not found with id: " + feedbackId);
		}

		Feedback existingFeedback = optionalFeedback.get();

		if (!existingFeedback.getUser().getUserId().equals(cus.getUserId()) && cus.getRole() != Role.ADMIN) {
			throw new UsersException("You don't have permission to update this feedback");
		}
		// Step 4: Delete the feedback
		feedbackRepo.delete(existingFeedback);

	}
}