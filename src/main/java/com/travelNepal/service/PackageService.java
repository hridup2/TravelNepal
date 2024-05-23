package com.travelNepal.service;

import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;

 public interface PackageService {
	
	 Package addPackage(String sessionId, Package pack) throws PackageException, LoginException, AdminException;
	
	 Package deletePackage(String sessionId,Integer packageId) throws PackageException, LoginException, AdminException;
	
	 List<Package> addAllPackages(List<Package> packages);
	
	 List<Package> viewAllPackages() throws PackageException;

	  Optional<Package> getPackageById(Integer packageId);

	 Package updatePackage(String sessionId, Integer packageId, Package updatedPackage) throws PackageException, LoginException, AdminException;
}