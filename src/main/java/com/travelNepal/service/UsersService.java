package com.travelNepal.service;


import java.util.List;

import com.travelNepal.entity.Users;
import com.travelNepal.exception.UsersException;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.LoginException;


public interface UsersService {

    public Users registerNewUser(Users user) throws UsersException;

    public Users updateUser(String sessionId, Users user) throws LoginException, UsersException;

    public Users deleteUser(String sessionId, Integer userId)
            throws LoginException, AdminException, UsersException;

    public Users getUsersBySessionId(String sessionId) throws LoginException, UsersException;

    public Users getUsersByUserId(String sessionId, Integer userId)
            throws LoginException, UsersException, AdminException;

    public List<Users> getAllUsers(String sessionId) throws LoginException, AdminException, UsersException;

}