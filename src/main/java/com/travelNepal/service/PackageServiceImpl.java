package com.travelNepal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.CurrentUserSession.Role;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.repository.PackageRepository;
import com.travelNepal.repository.SessionRepository;

@Service
public class PackageServiceImpl implements PackageService {

	// Autowired annotation for the PackageRepository dependency
	@Autowired
	private PackageRepository packageRepo;

	// Autowired annotation for the SessionRepository dependency
	@Autowired
	private SessionRepository sessRepo;

	@Override
	public Package addPackage(String sessionId, Package pack) throws PackageException, LoginException, AdminException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");

		if (cus.getRole() == Role.ADMIN) {
			return packageRepo.save(pack);
		} else {
			throw new AdminException("user not authorized");
		}

	}

	@Override
	public Package deletePackage(String sessionId, Integer packageId)
			throws PackageException, LoginException, AdminException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		if (cus.getRole() != Role.ADMIN) {
			throw new AdminException("user not authorized");
		}
		Optional<Package> pack = packageRepo.findById(packageId);
		if (pack.isEmpty())
			throw new PackageException("package not found");
		packageRepo.delete(pack.get());
		return pack.get();
	}

	@Override
	public List<Package> addAllPackages(List<Package> packages) {
        return packageRepo.saveAll(packages);
	}


	@Override
	public List<Package> viewAllPackages() throws PackageException {
		List<Package> packages = packageRepo.findAll();
		if (packages.size() == 0)
			throw new PackageException("package not found");
		return packages;
	}

	public Optional<Package> getPackageById(Integer packageId) {
		return packageRepo.findById(packageId);
	}

	public Package updatePackage(String sessionId, Integer packageId, Package updatedPackage) throws PackageException, LoginException, AdminException {
		// Check if the user is logged in
		CurrentUserSession currentUserSession = sessRepo.findBySessionId(sessionId);
		if (currentUserSession == null) {
			throw new LoginException("You are not logged in.");
		}

		// Check if the user is an admin
		if (currentUserSession.getRole() != Role.ADMIN) {
			throw new AdminException("User is not authorized.");
		}

		// Check if the package exists
		Optional<Package> packageOptional = packageRepo.findById(packageId);
		if (packageOptional.isEmpty()) {
			throw new PackageException("Package not found.");
		}

		// Get the existing package
		Package existingPackage = packageOptional.get();

		// Update the package properties
		existingPackage.setPackageName(updatedPackage.getPackageName());
		existingPackage.setPackageDescription(updatedPackage.getPackageDescription());
		existingPackage.setPackagePrice(updatedPackage.getPackagePrice());
		existingPackage.setDays(updatedPackage.getDays());
		existingPackage.setDestinationPhotoUrl(updatedPackage.getDestinationPhotoUrl());
		// Update other properties as necessary

		// Save the updated package
		return packageRepo.save(existingPackage);
	}


}
