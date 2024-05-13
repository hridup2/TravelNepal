package com.travelNepal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelNepal.dto.LoginDTO;
import com.travelNepal.dto.ResponseMessage;
import com.travelNepal.dto.SignupDTO;
import com.travelNepal.entity.Admin;
import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.CurrentUserSession.Role;
import com.travelNepal.entity.Users;
import com.travelNepal.exception.LoginException;
import com.travelNepal.repository.AdminRepository;
import com.travelNepal.repository.UserRepository;
import com.travelNepal.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private UserRepository userRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CurrentUserSession logIntoApplication(LoginDTO Dto) throws LoginException {

        Users user = userRepo.findByEmail(Dto.getEmail());
        if (user == null) {
            Admin admin = adminRepo.findByEmail(Dto.getEmail());
            if (admin == null) {
                throw new LoginException("Wrong Credential !");
            }
            if (Dto.getPassword().equals(admin.getPassword())) {

                CurrentUserSession cus = new CurrentUserSession();
                String sessionId = RandomKeyGenerator.generateRandomString(8);
                cus.setLoginTime(LocalDateTime.now());
                cus.setRole(Role.ADMIN);
                cus.setUserId(admin.getUserId());
                cus.setSessionId(sessionId);

                return sessionRepo.save(cus);
            } else {
                throw new LoginException("Wrong Credential!");
            }
        } else {

            Optional<CurrentUserSession> loginUserSession = sessionRepo.findById(user.getUserId());

            if (Dto.getPassword().equals(user.getPassword())) {

                if (loginUserSession.isPresent())
                    sessionRepo.delete(loginUserSession.get());

                CurrentUserSession cus = new CurrentUserSession();
                String sessionId = RandomKeyGenerator.generateRandomString(8);
                cus.setLoginTime(LocalDateTime.now());
                cus.setRole(Role.CUSTOMER);
                cus.setUserId(user.getUserId());
                cus.setSessionId(sessionId);

                return sessionRepo.save(cus);
            } else {
                throw new LoginException("Wrong Credential!");
            }
        }
    }

    @Override
    public ResponseMessage logoutFromApplication(String sessionId) throws LoginException {

        CurrentUserSession loginUserSession = sessionRepo.findBySessionId(sessionId);
        if (loginUserSession == null)
            throw new LoginException("Not login into application!");

        sessionRepo.delete(loginUserSession);
        return new ResponseMessage("Successfully logout...");
    }

    @Override
    public String createUser(SignupDTO signupDTO) {
        Users user = new Users();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(signupDTO.getPassword());

        Admin admin = objectMapper.convertValue(user, Admin.class);
        user = userRepo.save(user);
        admin.setPassword(signupDTO.getPassword());
        user.setPassword(signupDTO.getPassword());
        userRepo.save(user);
        adminRepo.save(admin);

        return "User Successfully Created";

    }

}
