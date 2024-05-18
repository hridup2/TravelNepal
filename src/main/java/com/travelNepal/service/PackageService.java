package com.travelNepal.service;

import java.util.List;

import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;

 public interface PackageService {
	
	 Package addPackage(String sessionId, Package pack) throws PackageException, LoginException, AdminException;
	
	 Package deletePackage(String sessionId,Integer packageId) throws PackageException, LoginException, AdminException;
	
	 Package searchPackage(Integer packageId) throws PackageException, LoginException, AdminException;
	
	 List<Package> addAllPackages(List<Package> packages);
	
	 List<Package> viewAllPackages() throws PackageException;
	
	 Package searchByPackageTitle(String packageTitle) throws PackageException, AdminException;
	
	 Package assignHotelToPackage(String sessionId,Integer hotelId,Integer packageId) throws PackageException,HotelException,LoginException, AdminException;

	 List<Hotel> getAvailableHotels(Integer packageId) throws PackageException,HotelException;
	
	 List<Hotel> getAllHotels(String sessionId,Integer packageId) throws PackageException,HotelException,AdminException,LoginException;

	 Package updatePackage(String sessionId, Integer packageId, Package updatedPackage) throws PackageException, LoginException, AdminException;
}