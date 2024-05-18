package com.travelNepal.controller;

import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.service.PackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
@CrossOrigin("*")
public class PackageController {

    @Autowired
    private PackageService packService;


    @PostMapping("/create/{sessionId}")
    public ResponseEntity<Package> addPackageController(@Valid @RequestBody Package pack, @PathVariable String sessionId) throws PackageException, LoginException, AdminException {
        Package pp = packService.addPackage(sessionId, pack);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{sessionId}")
    public ResponseEntity<Package> deletePackageController(@Valid @PathVariable String sessionId, @RequestParam("packageId") Integer packageId) throws PackageException, LoginException, AdminException {
        Package pp = packService.deletePackage(sessionId, packageId);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Package> searchPackageController(@Valid @RequestParam("packageId") Integer packageId) throws PackageException, LoginException, AdminException {
        Package pp = packService.searchPackage(packageId);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @GetMapping("/viewall")
    public ResponseEntity<List<Package>> viewAllPackageController() throws PackageException {
        List<Package> pp = packService.viewAllPackages();
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @PostMapping("/addPackages")
    public ResponseEntity<List<Package>> addPackages(@RequestBody List<Package> packages){
        return new ResponseEntity<>(packService.addAllPackages(packages),HttpStatus.OK);
    }

    @GetMapping("/searchByTitle/{packageTitle}")
    public ResponseEntity<Package> searchByTitlePackageController(@Valid @PathVariable String packageTitle) throws PackageException, LoginException, AdminException {
        Package pp = packService.searchByPackageTitle(packageTitle);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @PutMapping("/assignHotel/{sessionId}")
    public ResponseEntity<Package> assignHotelToPackageController(@Valid @PathVariable String sessionId, @RequestParam("hotelId") Integer hotelId, @RequestParam("packageId") Integer packageId) throws PackageException, LoginException, AdminException, HotelException {
        Package pp = packService.assignHotelToPackage(sessionId, hotelId, packageId);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @GetMapping("/getAvailableHotels/{packageId}")
    public ResponseEntity<List<Hotel>> getAvailableHotelsController(@Valid @PathVariable Integer packageId) throws PackageException, LoginException, AdminException, HotelException {
        List<Hotel> hotels = packService.getAvailableHotels(packageId);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }


    @GetMapping("/getAllHotels/{sessionId}")
    public ResponseEntity<List<Hotel>> getAllHotelsController(@Valid @PathVariable String sessionId, @RequestParam("packageId") Integer packageId) throws PackageException, LoginException, AdminException, HotelException {
        List<Hotel> hotels = packService.getAllHotels(sessionId, packageId);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    // Add the update package endpoint
    @PutMapping("/update/{sessionId}/{packageId}")
    public ResponseEntity<Package> updatePackageController(@Valid @PathVariable String sessionId, @PathVariable Integer packageId, @RequestBody Package updatedPackage) throws PackageException, LoginException, AdminException {
        Package pp = packService.updatePackage(sessionId, packageId, updatedPackage);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

}