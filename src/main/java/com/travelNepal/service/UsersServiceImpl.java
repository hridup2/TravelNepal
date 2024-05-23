package com.travelNepal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.CurrentUserSession.Role;
import com.travelNepal.entity.Users;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.UsersException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.repository.UserRepository;
import com.travelNepal.repository.SessionRepository;


@Service
public class UsersServiceImpl implements UsersService {

    //	@Autowired is used to resolve CustomerRepository dependency
    @Autowired
    private UserRepository userRepo;
    //	@Autowired is used to resolve SessionRepository dependency
    @Autowired
    private SessionRepository sessionRepo;


    @Override
    public Users registerNewUser(Users user) throws UsersException {

        if (user == null)
            throw new UsersException("Customer Details Not Found!");

        Users existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null)
            throw new UsersException("Customer Found with same Email !");

        // Send an email to reset the password
        String emailSubject = "Registration Successfull";
        String emailBody = "Dear " + user.getName()
                + ",\n\nThank you for creating your account on Imperial Trevels."
                + "\nWe hope our website will help you to provide best packages "
                + "in affordable price range and with the best Hotels and Transport services."
                + " We look forward to serving you. " + "\n\nBest regards,\nThe Imperial Travel Team";
//        emailService.sendEmail(customer.getEmail(), emailSubject, emailBody);
        return userRepo.save(user);
    }


    @Override
    public Users updateUser(String sessionId, Users user) throws LoginException, UsersException {

        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User Not Authorized!");

        Optional<Users> opt = userRepo.findById(cus.getUserId());

        if (opt.isEmpty())
            throw new UsersException("Customer not Found!");

        Users existingUser = opt.get();
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setName(user.getName());

        return userRepo.save(existingUser);
    }

    @Override
    public Users deleteUser(String sessionId, Integer userId)
            throws LoginException, AdminException, UsersException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User Not Authorized!");
        if (cus.getRole() == Role.ADMIN) {
            Optional<Users> opt = userRepo.findById(cus.getUserId());
            if (opt.isEmpty())
                throw new UsersException("Customer not Found in Record!");

            userRepo.delete(opt.get());
            return opt.get();
        }

        throw new AdminException("User Not Authorized!");

    }


    @Override
    public Users getUsersBySessionId(String sessionId) throws LoginException, UsersException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User Not Authorized!");
        Optional<Users> opt = userRepo.findById(cus.getUserId());
        if (opt.isEmpty())
            throw new UsersException("Customer not Found in Record!");

        return opt.get();
    }

    @Override
    public Users getUsersByUserId(String sessionId, Integer userId)
            throws LoginException, UsersException, AdminException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User Not Authorized!");
        if (cus.getRole() == Role.ADMIN) {
            Optional<Users> opt = userRepo.findById(userId);
            if (opt.isEmpty())
                throw new UsersException("Customer not Found in Record!");
            return opt.get();
        }

        throw new AdminException("User Not Authorized!");

    }

    @Override
    public List<Users> getAllUsers(String sessionId) throws LoginException, AdminException, UsersException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User Not Authorized!");
        if (cus.getRole() == Role.ADMIN) {
            List<Users> customers = userRepo.findAll();
            if (customers.isEmpty())
                throw new UsersException("Customer not Found in Record!");
            return customers;
        }

        throw new AdminException("User Not Authorized!");
    }
}
