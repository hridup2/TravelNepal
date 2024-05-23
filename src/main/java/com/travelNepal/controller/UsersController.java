package com.travelNepal.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travelNepal.entity.Users;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.UsersException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.service.UsersService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UsersController {

    @Autowired
    private UsersService userService;


    @PostMapping("/register")
    public ResponseEntity<Users> SaveUsers(@RequestBody Users user) throws UsersException {

        Users u = userService.registerNewUser(user);
        return new ResponseEntity<>(u, HttpStatus.CREATED);

    }


    @PutMapping("/update/{sessionId}")
    public ResponseEntity<Users> updateUser(@PathVariable String sessionId, @RequestBody Users user)
            throws UsersException, LoginException {

        Users newUser = userService.updateUser(sessionId, user);
        return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);

    }


    @DeleteMapping("/delete/{sessionId}/{userId}")
    public ResponseEntity<Users> deleteUser(@PathVariable String sessionId, @PathVariable Integer userId)
            throws UsersException, LoginException, AdminException {

        Users newUser = userService.deleteUser(sessionId, userId);
        return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);

    }


    @GetMapping("/getBySessionId/{sessionId}")
    public ResponseEntity<Users> getUsersBySessionId(@PathVariable String sessionId)
            throws LoginException, UsersException {

        Users user = userService.getUsersBySessionId(sessionId);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


    @GetMapping("/getByUserId/{sessionId}/{userId}")
    public ResponseEntity<Users> getUserByUserId(@PathVariable String sessionId,
                                                 @PathVariable Integer userId) throws LoginException, UsersException, AdminException {

        Users user = userService.getUsersByUserId(sessionId, userId);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


    @GetMapping("/getAllUsers/{sessionId}")
    public ResponseEntity<List<Users>> getAllCustomer(@PathVariable String sessionId)
            throws LoginException, UsersException, AdminException {

        List<Users> users = userService.getAllUsers(sessionId);
        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }

}

