package com.travelNepal.service;

import com.travelNepal.dto.LoginDTO;
import com.travelNepal.dto.ResponseMessage;
import com.travelNepal.dto.SignupDTO;
import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.exception.LoginException;


public interface AuthService {

    CurrentUserSession logIntoApplication(LoginDTO Dto) throws LoginException;

    ResponseMessage logoutFromApplication(String sessionId) throws LoginException;

    String createUser(SignupDTO signupDto);
}