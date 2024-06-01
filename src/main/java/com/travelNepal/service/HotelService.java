package com.travelNepal.service;

import java.util.List;

import com.travelNepal.entity.Hotel;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;


public interface HotelService {

    public Hotel registerNewHotel(String sessionId, Hotel hotel) throws LoginException, AdminException, HotelException;

    public Hotel updateHotelDetails(String sessionId, Hotel hotel)
            throws LoginException, AdminException, HotelException;

    public Hotel deleteHotelDetails(String sessionId, Integer hotelId)
            throws LoginException, AdminException, HotelException;

    public Hotel getHotelByHotelId(String sessionId, Integer hotelId)
            throws LoginException, AdminException, HotelException;

    public List<Hotel> getAllHotels(String sessionId) throws LoginException, AdminException, HotelException;

}