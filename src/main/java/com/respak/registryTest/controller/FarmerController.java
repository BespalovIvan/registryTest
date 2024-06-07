package com.respak.registryTest.controller;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.dto.FarmerDto;
import com.respak.registryTest.service.FarmerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {
    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @PostMapping
    public ResponseEntity<String> createFarmer(@RequestBody FarmerDto farmerDto) {
        farmerService.addFarmer(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<FarmerDto>> findAllActiveFarmers() {
        return new ResponseEntity<>(farmerService.findAllActiveFarmers(), HttpStatus.OK);
    }

    @PutMapping("/toArchive")
    public ResponseEntity<String> makeArchival(@RequestBody FarmerDto farmerDto) {
        farmerService.toArchive(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reArchive")
    public ResponseEntity<String> reArchival(@RequestBody FarmerDto farmerDto) {
        farmerService.reArchive(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateFarmer(@RequestBody FarmerDto farmerDto) {
        farmerService.updateFarmer(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/organizationName/{name}")
    public ResponseEntity<FarmerDto> findActiveFarmerByOrganizationName(@PathVariable("name") String name) {
        return new ResponseEntity<>(farmerService.findActiveFarmerByOrganizationName(name), HttpStatus.OK);
    }

    @GetMapping("/registrationArea/{registrationAreaId}")
    public ResponseEntity<List<FarmerDto>> findActiveFarmerByRegistrationArea(@PathVariable("registrationAreaId")
                                                                              UUID areaId) {
        return new ResponseEntity<>(farmerService.findActiveFarmerByRegistrationArea(areaId), HttpStatus.OK);
    }

    @GetMapping("/inn/{inn}")
    public ResponseEntity<FarmerDto> findActiveFarmerByInn(@PathVariable("inn") Long inn) {
        return new ResponseEntity<>(farmerService.findActiveFarmerByInn(inn), HttpStatus.OK);
    }

    @GetMapping("/isArchive/{isArchive}")
    public ResponseEntity<List<FarmerDto>> findActiveFarmerByIsArchive(@PathVariable("isArchive") Boolean isArchive) {
        return new ResponseEntity<>(farmerService.findByIsArchive(isArchive), HttpStatus.OK);
    }

    @GetMapping("/registrationDate/{registrationDate}")
    public ResponseEntity<List<FarmerDto>> findActiveFarmerByRegistrationDate(@PathVariable("registrationDate")
                                                                              String registrationDate) {
        return new ResponseEntity<>(farmerService.findByRegistrationDate(registrationDate), HttpStatus.OK);
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<String> registerFarmer(@PathVariable("id") UUID farmerId, @RequestBody AreaDto areaDto) {
        farmerService.registerFarmer(farmerId, areaDto.getAreaId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addCropField/{id}")
    public ResponseEntity<String> addCropField(@PathVariable("id") UUID farmerId, @RequestBody AreaDto areaDto) {
        farmerService.addCropFieldToFarmer(farmerId, areaDto.getAreaId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
