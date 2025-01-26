package com.travelNepal.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;

public interface PackageService {

	Package addPackage(String sessionId, Package pack) throws PackageException, LoginException, AdminException, IOException;

	Package deletePackage(String sessionId,Integer packageId) throws PackageException, LoginException, AdminException;

	List<Package> addAllPackages(List<Package> packages);

	List<Package> viewAllPackages() throws PackageException;

	Optional<Package> getPackageById(Integer packageId);

	Package updatePackage(String sessionId, Integer packageId, Package packageData) throws PackageException, LoginException, AdminException;

	public Package assignHotelToPackage(String sessionId,Integer hotelId,Integer packageId) throws PackageException, HotelException,LoginException, AdminException;

//	public List<Hotel> getAvailableHotels(Integer packageId) throws PackageException,HotelException;

	public List<Hotel> getAllHotels(Integer packageId) throws PackageException,HotelException;

}