package com.travelNepal.controller;


import com.travelNepal.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelNepal.dto.LoginDTO;
import com.travelNepal.dto.SignupDTO;
import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.exception.LoginException;
import com.travelNepal.service.UsersService;
import com.travelNepal.service.AuthService;

@RestController
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private AuthService loginService;

	@Autowired
	private UsersService userService;
	/**
	 *
	 * @param dto  				The User Credentials
	 * @return 					The Current User Session with Session ID
	 * @throws LoginException 	If User Not Registered
	 */
	@PostMapping("/login")
	public ResponseEntity<CurrentUserSession> loginIntoApplication(@RequestBody LoginDTO dto) throws LoginException{
		CurrentUserSession cus = loginService.logIntoApplication(dto);

		return new ResponseEntity<>(cus,HttpStatus.ACCEPTED);
	}

	@PostMapping("/signup")
	public String signup(@RequestBody SignupDTO signupDTO){


		return loginService.createUser(signupDTO);
	}
	/**
	 *
	 * @param sessionId			The Unique Session Id
	 * @return					Response Message
	 * @throws LoginException	If User Not Login
	 */
	@PostMapping("/logout")
	public ResponseEntity<ResponseMessage> logoutFromApplication(@RequestParam("sessionId") String sessionId) throws LoginException{
		ResponseMessage responseMessage = loginService.logoutFromApplication(sessionId);

		return new ResponseEntity<>(responseMessage,HttpStatus.ACCEPTED);
	}

}
