package com.travelNepal.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travelNepal.entity.Hotel;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.service.HotelService;

@RestController
@RequestMapping("/hotels")
@CrossOrigin("*")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/registerHotel/{sessionId}")
    public ResponseEntity<Hotel> registerNewHotel(@PathVariable String sessionId, @RequestBody Hotel hotel)
            throws LoginException, AdminException, HotelException {

        Hotel newHotel = hotelService.registerNewHotel(sessionId, hotel);
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    @PutMapping("/updateHotel/{sessionId}/{hotelId}")
    public ResponseEntity<Hotel> updateHotelDetails(@Valid @PathVariable String sessionId, @PathVariable Integer hotelId, @RequestBody Hotel hotelData)
            throws LoginException, AdminException, HotelException {

        Hotel existingHotel = hotelService.updateHotelDetails(sessionId, hotelId, hotelData);
        return new ResponseEntity<>(existingHotel, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteHotel/{sessionId}/{hotelId}")
    public ResponseEntity<Hotel> deleteHotelDetails(@PathVariable String sessionId, @PathVariable Integer hotelId)
            throws LoginException, AdminException, HotelException {

        Hotel hotel = hotelService.deleteHotelDetails(sessionId, hotelId);
        return new ResponseEntity<>(hotel, HttpStatus.ACCEPTED);
    }

    @GetMapping("/hotelDetailsById/{hotelId}")
    public ResponseEntity<Hotel> getHotelByHotelId( @PathVariable Integer hotelId)
            throws LoginException, AdminException, HotelException {

       Optional<Hotel> hotel = hotelService.getHotelByHotelId(hotelId);
        return hotel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/allHotelsDetails")
    public ResponseEntity<List<Hotel>> getAllHotelsDetails()
            throws  HotelException {

        List<Hotel> hotels = hotelService.getAllHotelsDetails();
        return new ResponseEntity<>(hotels, HttpStatus.ACCEPTED);
    }




}