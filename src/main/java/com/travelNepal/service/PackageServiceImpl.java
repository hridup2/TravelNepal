package com.travelNepal.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.repository.PackageRepository;
import com.travelNepal.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepo;

    @Autowired
    private SessionRepository sessRepo;

    @Override
    public Package addPackage(String sessionId, Package pack, MultipartFile photo) throws PackageException, LoginException, AdminException, IOException {
        CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
        if (cus == null) {
            throw new LoginException("You're not logged in");
        }

        if (cus.getRole() == CurrentUserSession.Role.ADMIN) {
            if (photo != null && !photo.isEmpty()) {
                pack.setDestinationPhotoUrl(Arrays.toString(photo.getBytes()));
            }
            return packageRepo.save(pack);
        } else {
            throw new AdminException("User not authorized");
        }
    }

    @Override
    public Package deletePackage(String sessionId, Integer packageId) throws PackageException, LoginException, AdminException {
        CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
        if (cus == null)
            throw new LoginException("You're not logged in");
        if (cus.getRole() != CurrentUserSession.Role.ADMIN) {
            throw new AdminException("User not authorized");
        }
        Optional<Package> pack = packageRepo.findById(packageId);
        if (pack.isEmpty())
            throw new PackageException("Package not found");
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
        if (packages.isEmpty())
            throw new PackageException("Package not found");
        return packages;
    }

    @Override
    public Optional<Package> getPackageById(Integer packageId) {
        return Optional.ofNullable(packageRepo.findByPackageId(packageId));
    }

    @Override
    public Package updatePackage(String sessionId, Integer packageId, Package updatedPackage) throws PackageException, LoginException, AdminException {
        CurrentUserSession currentUserSession = sessRepo.findBySessionId(sessionId);
        if (currentUserSession == null) {
            throw new LoginException("You are not logged in.");
        }

        if (currentUserSession.getRole() != CurrentUserSession.Role.ADMIN) {
            throw new AdminException("User is not authorized.");
        }

        Optional<Package> packageOptional = packageRepo.findById(packageId);
        if (packageOptional.isEmpty()) {
            throw new PackageException("Package not found.");
        }

        Package existingPackage = packageOptional.get();
        existingPackage.setPackageName(updatedPackage.getPackageName());
        existingPackage.setPackageDescription(updatedPackage.getPackageDescription());
        existingPackage.setPackagePrice(updatedPackage.getPackagePrice());
        existingPackage.setDays(updatedPackage.getDays());
        existingPackage.setDestinationPhotoUrl(updatedPackage.getDestinationPhotoUrl());

        return packageRepo.save(existingPackage);
    }
}