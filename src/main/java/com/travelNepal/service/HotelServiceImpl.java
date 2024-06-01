package com.travelNepal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.CurrentUserSession.Role;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.repository.HotelRepository;
import com.travelNepal.repository.SessionRepository;

/**
 * @author Aman_Maurya
 *
 */

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private SessionRepository sessionRepo;

    @Override
    public Hotel registerNewHotel(String sessionId, Hotel hotel) throws LoginException, AdminException, HotelException {

        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User not Logged In!");
        if (cus.getRole() == Role.ADMIN) {
            if (hotel == null)
                throw new HotelException("Hotel Details is Mandatory!");

            return hotelRepo.save(hotel);
        }
        throw new AdminException("User not Authorized!");
    }

    @Override
    public Hotel updateHotelDetails(String sessionId, Hotel hotel)
            throws LoginException, AdminException, HotelException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User not Logged In!");
        if (cus.getRole() == Role.ADMIN) {
            Optional<Hotel> hotelOpt = hotelRepo.findById(hotel.getHotelId());
            if (hotelOpt.isEmpty())
                throw new HotelException("Hotel Details Found in Record!");
            else if (hotel == null)
                throw new HotelException("Hotel Details is Mandatory!");
            else
                return hotelRepo.save(hotel);
        }
        throw new AdminException("User not Authorized!");
    }

    @Override
    public Hotel deleteHotelDetails(String sessionId, Integer hotelId)
            throws LoginException, AdminException, HotelException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User not Logged In!");
        if (cus.getRole() == Role.ADMIN) {
            Optional<Hotel> hotelOpt = hotelRepo.findById(hotelId);
            if (hotelOpt.isEmpty())
                throw new HotelException("Hotel Details Found in Record!");

            hotelRepo.delete(hotelOpt.get());
            return hotelOpt.get();
        }
        throw new AdminException("User not Authorized!");
    }

    @Override
    public Hotel getHotelByHotelId(String sessionId, Integer hotelId)
            throws LoginException, AdminException, HotelException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User not Logged In!");
        if (cus.getRole() == Role.ADMIN) {
            Optional<Hotel> hotelOpt = hotelRepo.findById(hotelId);
            if (hotelOpt.isEmpty())
                throw new HotelException("Hotel Details Found in Record!");

            return hotelOpt.get();
        }
        throw new AdminException("User not Authorized!");
    }

    @Override
    public List<Hotel> getAllHotels(String sessionId) throws LoginException, AdminException, HotelException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null)
            throw new LoginException("User not Logged In!");
        if (cus.getRole() == Role.ADMIN) {
            List<Hotel> hotels = hotelRepo.findAll();
            if (hotels.isEmpty())
                throw new HotelException("Hotel Details Found in Record!");

            return hotels;
        }
        throw new AdminException("User not Authorized!");
    }

}