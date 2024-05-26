package com.travelNepal.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import org.springframework.web.multipart.MultipartFile;

public interface PackageService {

	Package addPackage(String sessionId, Package pack, MultipartFile photo ) throws PackageException, LoginException, AdminException, IOException;

	Package deletePackage(String sessionId,Integer packageId) throws PackageException, LoginException, AdminException;

	List<Package> addAllPackages(List<Package> packages);

	List<Package> viewAllPackages() throws PackageException;

	Optional<Package> getPackageById(Integer packageId);

	Package updatePackage(String sessionId, Integer packageId, Package updatedPackage) throws PackageException, LoginException, AdminException;
}