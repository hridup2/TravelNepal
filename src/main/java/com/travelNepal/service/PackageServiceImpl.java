package com.travelNepal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.Package;
import com.travelNepal.enums.PackageStatus;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.repository.HotelRepository;
import com.travelNepal.repository.PackageRepository;
import com.travelNepal.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepo;

    @Autowired
    private SessionRepository sessRepo;

    @Autowired
    private HotelRepository hotelRepo;

    @Override
    public Package addPackage(String sessionId, Package pack) throws PackageException, LoginException, AdminException, IOException {
        CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
        if (cus == null) {
            throw new LoginException("You're not logged in");
        }

        if (cus.getRole() == CurrentUserSession.Role.ADMIN) {
            pack.setDestinationPhotoUrl(pack.getDestinationPhotoUrl());
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

        pack.get().setPackageStatus(PackageStatus.DELETED);

        packageRepo.save(pack.get());
        return pack.get();
    }

    @Override
    public List<Package> addAllPackages(List<Package> packages) {
        return packageRepo.saveAll(packages);
    }

    @Override
    public List<Package> viewAllPackages() throws PackageException {
        List<Package> packages = packageRepo.getAllPackages();
        if (packages.isEmpty())
            throw new PackageException("Package not found");
        return packages;
    }

    @Override
    public Optional<Package> getPackageById(Integer packageId) {
        return Optional.ofNullable(packageRepo.findByPackageId(packageId));
    }

    @Override
    public Package updatePackage(String sessionId, Integer packageId, Package packageData) throws PackageException, LoginException, AdminException {
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
        existingPackage.setPackageName(packageData.getPackageName());
        existingPackage.setPackageDescription(packageData.getPackageDescription());
        existingPackage.setPackagePrice(packageData.getPackagePrice());
        existingPackage.setDays(packageData.getDays());
        existingPackage.setDestinationPhotoUrl(packageData.getDestinationPhotoUrl());

        return packageRepo.save(existingPackage);
    }

    @Override
    public Package assignHotelToPackage(String sessionId, Integer hotelId, Integer packageId)
            throws PackageException, HotelException, LoginException, AdminException {
        CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
        if (cus == null)
            throw new LoginException("you're not logged in");
        if (cus.getRole() != CurrentUserSession.Role.ADMIN) {
            throw new AdminException("user not authorized");
        }
        Optional<Hotel> hotel = hotelRepo.findById(hotelId);
        if (hotel.isEmpty())
            throw new HotelException("hotel not found");
        Optional<Package> pack = packageRepo.findById(packageId);
        if (pack.isEmpty())
            throw new PackageException("package not found");

        Package packa = pack.get();
        Hotel hot = hotel.get();
        packa.getHotels().add(hot);
        hot.getPackages().add(packa);

        packageRepo.save(packa);
        hotelRepo.save(hot);

        return packa;
    }

    @Override
    public List<Hotel> getAvailableHotels(Integer packageId) throws PackageException, HotelException {
        List<Object[]> hotelsObj = packageRepo.getAvailableHotels(packageId);
        if (hotelsObj.isEmpty()) {
            throw new HotelException("hotel not found");
        }
        List<Hotel> hotels = new ArrayList<>();
        for (Object[] obj : hotelsObj) {

            Integer id = (Integer) obj[0];
            String hotelAddress = (String) obj[1];
            String hotelDescription = (String) obj[2];
            String name = (String) obj[3];
            Double hotelRent = (Double) obj[4];
            Hotel.HotelType ht = Hotel.HotelType.valueOf((String) obj[5]);
            boolean isAvailable = (Boolean) obj[6];
            String email = (String) obj[7];

            Hotel h = new Hotel(id, name, email, hotelDescription, ht, hotelRent, hotelAddress, isAvailable);
            hotels.add(h);
        }

        return hotels;
    }

    @Override
    public List<Hotel> getAllHotels(String sessionId, Integer packageId)
            throws PackageException, HotelException, AdminException, LoginException {
        CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
        if (cus == null)
            throw new LoginException("you're not logged in");
        if (cus.getRole() != CurrentUserSession.Role.ADMIN) {
            throw new AdminException("user not authorized");
        }
        List<Object[]> hotelsObj = packageRepo.getAllHotels(packageId);
        if (hotelsObj.isEmpty()) {
            throw new HotelException("hotel not found");
        }
        List<Hotel> hotels = new ArrayList<>();
        for (Object[] obj : hotelsObj) {

            Integer id = (Integer) obj[0];
            String hotelAddress = (String) obj[1];
            String hotelDescription = (String) obj[2];
            String name = (String) obj[3];
            Double hotelRent = (Double) obj[4];
            Hotel.HotelType ht = Hotel.HotelType.valueOf(((String) obj[5]).toUpperCase());
            boolean isAvailable = (Boolean) obj[6];
            String email = (String) obj[7];

            Hotel h = new Hotel(id, name, email, hotelDescription, ht, hotelRent, hotelAddress, isAvailable);
            hotels.add(h);

        }

        return hotels;
    }

}