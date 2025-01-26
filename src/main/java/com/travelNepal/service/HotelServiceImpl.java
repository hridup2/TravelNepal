package com.travelNepal.service;

import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.Package;
import com.travelNepal.enums.HotelStatus;
import com.travelNepal.enums.PackageStatus;
import com.travelNepal.exception.PackageException;
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
            hotel.setHotelPhotoUrl(hotel.getHotelPhotoUrl());
            hotel.setHotelStatus(HotelStatus.ACTIVE);
            return hotelRepo.save(hotel);
        }
        throw new AdminException("User not Authorized!");
    }

    @Override
    public Hotel updateHotelDetails(String sessionId, Integer hotelId, Hotel hotelData)
            throws LoginException, AdminException, HotelException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);

        if (cus == null) {
            throw new LoginException("User not Logged In!");
        }
        if (cus.getRole() != CurrentUserSession.Role.ADMIN) {
            throw new AdminException("User not Authorized!");
        }
            Optional<Hotel> hotelOpt = hotelRepo.findById(hotelId);
            if (hotelOpt.isEmpty()){
                throw new HotelException("Hotel Details Found in Record!");
        }

        // Update the hotel details
        Hotel existingHotel = hotelOpt.get();
        existingHotel.setHotelName(hotelData.getHotelName());
        existingHotel.setHotelPhotoUrl(hotelData.getHotelPhotoUrl());
        existingHotel.setEmail(hotelData.getEmail());
        existingHotel.setHotelDescription(hotelData.getHotelDescription());
        existingHotel.setHotelRent(hotelData.getHotelRent());
        existingHotel.setHotelAddress(hotelData.getHotelAddress());

        // Save the updated hotel and return it
        return hotelRepo.save(existingHotel);

    }

    @Override
    public Hotel deleteHotelDetails(String sessionId, Integer hotelId)
            throws LoginException, AdminException, HotelException {
        CurrentUserSession cus = sessionRepo.findBySessionId(sessionId);
        if (cus == null)
            throw new LoginException("You're not logged in");
        if (cus.getRole() != CurrentUserSession.Role.ADMIN) {
            throw new AdminException("User not authorized");
        }
        Optional<Hotel> hotel = hotelRepo.findById(hotelId);
        if (hotel.isEmpty())
            throw new PackageException("Package not found");

        hotel.get().setHotelStatus(HotelStatus.DELETED);

        hotelRepo.save(hotel.get());
        return hotel.get();
    }

    @Override
    public Optional<Hotel> getHotelByHotelId(Integer hotelId) throws HotelException {
        Optional<Hotel> hotelOpt = hotelRepo.findById(hotelId);

        if (hotelOpt.isEmpty()) {
            throw new HotelException("Hotel not found with ID: " + hotelId);
        }

        return hotelOpt;
    }

    @Override
    public List<Hotel> getAllHotelsDetails() throws HotelException {
        List<Hotel> hotel = hotelRepo.findAllHotel();
        if (hotel.isEmpty())
            throw new PackageException("Package not found");
        return hotel;
    }

}