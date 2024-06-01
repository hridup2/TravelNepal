package com.travelNepal.repository;

import com.travelNepal.entity.Hotel;
import com.travelNepal.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Integer> {

    Package findByPackageId(Integer packageId);


    @Query(value = "select * from hotel h where h.hotel_id IN (select ph.hotel_id from package_hotel ph where ph.package_id=:id group by ph.package_id) ", nativeQuery = true)
    List<Object[]> getAllHotels(@Param("id") Integer id);

    @Query(value = "SELECT * FROM package WHERE package_status = 'DELETED'", nativeQuery = true)
    List<Package> getAllPackages();


    @Query(value = "select * from hotel where hotel_id IN (select hotel_id from package_hotel where package_id=:id group by package_id) and is_available='1'", nativeQuery = true)
    List<Object[]> getAvailableHotels(@Param("id") Integer id);

}