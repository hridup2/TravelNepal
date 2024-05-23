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

		Users us = user.get();
		Optional<Package> pack = packageRepo.findById(packageId) ;
		pack.get().getFeedbacks().add(feedback);
		feedbackRepo.save(feedback);

		return new FeedbackResponse(LocalDateTime.now(), "Feedback sucessfully submitted");
	}

	@Override
	public List<Feedback> getFeedbackByPackageId(String sessionId, int packageid) throws FeedbackException, PackageException, LoginException{
		// TODO Auto-generated method stub
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("You're not logged in");
		if (cus.getRole() != Role.ADMIN) {
			throw new LoginException("Incorrect Credentials");
		}

		Optional<Package> pack = packageRepo.findById(packageid);
		if(pack==null) throw new PackageException("Package not found with the same id");
		List<Feedback> list = pack.get().getFeedbacks();
		if(list==null) throw new FeedbackException("Feedback not found");
		return list;
	}

	@Override
	public FeedbackResponse updateFeedback(Feedback feedback, String sessionId, Integer feedbackId) throws LoginException, UsersException, FeedbackException {
		return null;
	}


	public void deleteFeedback(String sessionId, Integer feedbackId) throws LoginException, UsersException, FeedbackException {
	}
}