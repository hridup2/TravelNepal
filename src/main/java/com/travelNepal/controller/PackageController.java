package com.travelNepal.controller;

import com.travelNepal.entity.Package;
import com.travelNepal.exception.AdminException;
import com.travelNepal.exception.LoginException;
import com.travelNepal.exception.PackageException;
import com.travelNepal.service.PackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/packages")
@CrossOrigin("*")
public class PackageController {

    @Autowired
    private PackageService packService;


    @PostMapping("/create/{sessionId}")
    public ResponseEntity<Package> addPackageController(@Valid @RequestBody Package pack, @PathVariable String sessionId) throws PackageException, LoginException, AdminException {
        Package pp = packService.addPackage(sessionId, pack);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{sessionId}")
    public ResponseEntity<Package> deletePackageController(@Valid @PathVariable String sessionId, @RequestParam("packageId") Integer packageId) throws PackageException, LoginException, AdminException {
        Package pp = packService.deletePackage(sessionId, packageId);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }


    @GetMapping("/viewall")
    public ResponseEntity<List<Package>> viewAllPackageController() throws PackageException {
        List<Package> pp = packService.viewAllPackages();
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<Package> getPackageById(@PathVariable Integer packageId) {
        Optional<Package> pp = packService.getPackageById(packageId);
        return pp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/addPackages")
    public ResponseEntity<List<Package>> addPackages(@RequestBody List<Package> packages){
        return new ResponseEntity<>(packService.addAllPackages(packages),HttpStatus.OK);
    }



    // Add the update package endpoint
    @PutMapping("/update/{sessionId}/{packageId}")
    public ResponseEntity<Package> updatePackageController(@Valid @PathVariable String sessionId, @PathVariable Integer packageId, @RequestBody Package updatedPackage) throws PackageException, LoginException, AdminException {
        Package pp = packService.updatePackage(sessionId, packageId, updatedPackage);
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

}