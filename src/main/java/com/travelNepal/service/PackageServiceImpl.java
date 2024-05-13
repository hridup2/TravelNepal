package com.travelNepal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelNepal.entity.CurrentUserSession;
import com.travelNepal.entity.CurrentUserSession.Role;
import com.travelNepal.entity.Hotel.HotelType;
import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.HotelException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.repository.HotelRepository;
import com.travelNepal.repository.PackageRepository;
import com.travelNepal.repository.SessionRepository;

@Service
public class PackageServiceImpl implements PackageService {

	// Autowired annotation for the PackageRepository dependency
	@Autowired
	private PackageRepository packageRepo;

	// Autowired annotation for the HotelRepository dependency
	@Autowired
	private HotelRepository hotelRepo;

	// Autowired annotation for the SessionRepository dependency
	@Autowired
	private SessionRepository sessRepo;

	/**
	 * Adds a new package to the system.
	 *
	 * @param sessionId The session ID of the logged-in user.
	 * @param pack      The package to be added.
	 * @return The added package.
	 * @throws PackageException If there is an issue with the package.
	 * @throws LoginException   If the user is not logged in.
	 * @throws AdminException   If the user is not authorized as an admin.
	 */
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

	/**
	 * Deletes a package from the system.
	 *
	 * @param sessionId The session ID of the logged-in user.
	 * @param packageId The ID of the package to be deleted.
	 * @return The deleted package.
	 * @throws PackageException If the package is not found.
	 * @throws LoginException   If the user is not logged in.
	 * @throws AdminException   If the user is not authorized as an admin.
	 */
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

	/**
	 * Searches for a package by its ID.
	 *
	 * @param packageId The ID of the package to search for.
	 * @return The found package.
	 * @throws PackageException If the package is not found.
	 */
	@Override
	public Package searchPackage(Integer packageId) throws PackageException, LoginException, AdminException {
		Optional<Package> pack = packageRepo.findById(packageId);
		if (pack.isEmpty())
			throw new PackageException("package not found");
		return pack.get();
	}

	/**
	 * Retrieves all packages in the system.
	 *
	 * @return A list of all packages.
	 * @throws PackageException If no packages are found.
	 */
	@Override
	public List<Package> viewAllPackages() throws PackageException, LoginException, AdminException {
		List<Package> packages = packageRepo.findAll();
		if (packages.size() == 0)
			throw new PackageException("package not found");
		return packages;
	}

	/**
	 * Searches for a package by its title.
	 *
	 * @param packageTitle The title of the package to search for.
	 * @return The found package.
	 * @throws PackageException If the package is not found.
	 */
	@Override
	public Package searchByPackageTitle(String packageTitle) throws PackageException, AdminException {
		Package packa = packageRepo.findByPackageName(packageTitle);
		if (packa == null)
			throw new PackageException("package not found");
		return packa;
	}

	/**
	 * Assigns a hotel to a package.
	 *
	 * @param sessionId The session ID of the logged-in user.
	 * @param hotelId   The ID of the hotel to assign.
	 * @param packageId The ID of the package to assign the hotel to.
	 * @return The updated package.
	 * @throws PackageException If the package is not found.
	 * @throws HotelException   If the hotel is not found.
	 * @throws LoginException   If the user is not logged in.
	 * @throws AdminException   If the user is not authorized as an admin.
	 */
	@Override
	public Package assignHotelToPackage(String sessionId, Integer hotelId, Integer packageId)
			throws PackageException, HotelException, LoginException, AdminException {
		CurrentUserSession cus = sessRepo.findBySessionId(sessionId);
		if (cus == null)
			throw new LoginException("you're not logged in");
		if (cus.getRole() != Role.ADMIN) {
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
		if (hotelsObj.size() == 0) {
			throw new HotelException("hotel not found");
		}
		List<Hotel> hotels = new ArrayList<>();
		for (Object[] obj : hotelsObj) {

			Integer id = (Integer) obj[0];
			String hotelAddress = (String) obj[1];
			String hotelDescription = (String) obj[2];
			String name = (String) obj[3];
			Double hotelRent = (Double) obj[4];
			HotelType ht = HotelType.valueOf((String) obj[5]);
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
		if (cus.getRole() != Role.ADMIN) {
			throw new AdminException("user not authorized");
		}
		List<Object[]> hotelsObj = packageRepo.getAllHotels(packageId);
		if (hotelsObj.size() == 0) {
			throw new HotelException("hotel not found");
		}
		List<Hotel> hotels = new ArrayList<>();
		for (Object[] obj : hotelsObj) {

			Integer id = (Integer) obj[0];
			String hotelAddress = (String) obj[1];
			String hotelDescription = (String) obj[2];
			String name = (String) obj[3];
			Double hotelRent = (Double) obj[4];
			HotelType ht = HotelType.valueOf(((String) obj[5]).toUpperCase());
			boolean isAvailable = (Boolean) obj[6];
			String email = (String) obj[7];

			Hotel h = new Hotel(id, name, email, hotelDescription, ht, hotelRent, hotelAddress, isAvailable);
			hotels.add(h);

		}

		return hotels;
	}

}
