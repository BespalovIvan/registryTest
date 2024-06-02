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

    @PostMapping("")
    public ResponseEntity<String> createFarmer(@RequestBody FarmerDto farmerDto) {
        farmerService.addFarmer(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<List<FarmerDto>> findAllActiveFarmers() {
        return new ResponseEntity<>(farmerService.findAllActiveFarmers(), HttpStatus.OK);
    }

    @PutMapping("/archive")
    public ResponseEntity<String> makeArchival(@RequestBody FarmerDto farmerDto) {
        farmerService.toArchive(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/organizationName/{name}")
    public ResponseEntity<FarmerDto> findByOrganizationName(@PathVariable("name") String name) {
        return new ResponseEntity<>(farmerService.findActiveFarmerByOrganizationName(name), HttpStatus.OK);
    }
    @PostMapping("/register/{id}")
    public ResponseEntity<String> registerFarmer(@PathVariable("id") UUID farmerId, @RequestBody AreaDto areaDto) {
        farmerService.registerFarmer(farmerId,areaDto.getAreaId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
