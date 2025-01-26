package com.travelNepal.repository;

import com.travelNepal.entity.Booking;
import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.Package;
import com.travelNepal.enums.BookingStatus;
import com.travelNepal.enums.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package, Integer> {

    Package findByPackageId(Integer packageId);


//    @Query(value = "SELECT * FROM hotel h WHERE h.hotel_id IN (SELECT ph.hotel_id FROM package_hotel ph WHERE ph.package_id =:id GROUP BY ph.package_id)", nativeQuery = true)
    @Query("SELECT h FROM Hotel h JOIN h.packages p WHERE p.id = :id")
    List<Hotel> getAllHotels(@Param("id") Integer packageId);

    @Query(value = "SELECT * FROM package WHERE package_status = 'DELETED'", nativeQuery = true)
    List<Package> getAllPackages();

}