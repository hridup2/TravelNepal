package com.travelNepal.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.travelNepal.entity.Users;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.UsersException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.service.UsersService;

@RestController("/users")
@CrossOrigin("*")
public class UsersController {

    @Autowired
    private UsersService userService;

    /**
     *
     * @param user				The Customer Details To Register
     * @return						Registered Customer Details
     * @throws UsersException	If Customer Details Not Found
     */
    @PostMapping("/register")
    public ResponseEntity<Users> SaveUsers(@RequestBody Users user) throws UsersException {

        Users u = userService.registerNewUser(user);
        return new ResponseEntity<>(u, HttpStatus.CREATED);

    }

    /**
     *
     * @param sessionId				The Unique Session Id
     * @param user				New Customer Details
     * @return						Updated Customer Details
     * @throws UsersException	If Customer Not Found
     * @throws LoginException		If User Not Login
     */
    @PutMapping("/update/{sessionId}")
    public ResponseEntity<Users> updateUser(@PathVariable String sessionId, @RequestBody Users user)
            throws UsersException, LoginException {

        Users newUser = userService.updateUser(sessionId, user);
        return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);

    }

    /**
     *
     * @param sessionId				The Unique Session Id
     * @param userId			The Unique Customer Id
     * @return						Deleted Customer Details
     * @throws UsersException	If Customer Not Found
     * @throws LoginException		If User Not Login
     * @throws AdminException		If User Not Authorized
     */
    @DeleteMapping("/delete/{sessionId}/{userId}")
    public ResponseEntity<Users> deleteUser(@PathVariable String sessionId, @PathVariable Integer userId)
            throws UsersException, LoginException, AdminException {

        Users newUser = userService.deleteUser(sessionId, userId);
        return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);

    }

    /**
     *
     * @param sessionId				The Unique Session Id
     * @return						The Customer Details
     * @throws LoginException		If User Not Login
     * @throws UsersException	If Customer Not Found
     */
    @GetMapping("/getBySessionId/{sessionId}")
    public ResponseEntity<Users> getUsersBySessionId(@PathVariable String sessionId)
            throws LoginException, UsersException {

        Users user = userService.getUsersBySessionId(sessionId);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    /**
     *
     * @param sessionId				The Unique Session Id
     * @param userId			The Unique Customer Id
     * @return						The Customer Details
     * @throws LoginException		If User Not Login
     * @throws UsersException	If Customer Not Found
     * @throws AdminException		If User Not Authorized
     */
    @GetMapping("/getByUserId/{sessionId}/{userId}")
    public ResponseEntity<Users> getUserByUserId(@PathVariable String sessionId,
                                                 @PathVariable Integer userId) throws LoginException, UsersException, AdminException {

        Users user = userService.getUsersByUserId(sessionId, userId);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    /**
     *
     * @param sessionId				The Unique Session Id
     * @return						All Registered Customer Details
     * @throws LoginException		If User Not Login
     * @throws UsersException	If Customer Not Found
     * @throws AdminException		If User Not Authorized
     */
    @GetMapping("/getAllUsers/{sessionId}")
    public ResponseEntity<List<Users>> getAllCustomer(@PathVariable String sessionId)
            throws LoginException, UsersException, AdminException {

        List<Users> users = userService.getAllUsers(sessionId);
        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }

}

