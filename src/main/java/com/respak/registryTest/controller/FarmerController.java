package com.respak.registryTest.controller;

import com.respak.registryTest.dto.FarmerDto;
import com.respak.registryTest.service.FarmerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FarmerController {
    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @PostMapping("/farmers")
    public ResponseEntity<String> createFarmer(@RequestBody FarmerDto farmerDto) {
        farmerService.addFarmer(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/farmers")
    public ResponseEntity<List<FarmerDto>> findAllFarmers() {
        return new ResponseEntity<>(farmerService.findAllFarmers(), HttpStatus.OK);
    }

    @PutMapping("/farmers/archive")
    public ResponseEntity<String> makeArchival(@RequestBody FarmerDto farmerDto) {
        farmerService.toArchive(farmerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/farmers/organizationName/{name}")
    public ResponseEntity<FarmerDto> findByOrganizationName(@PathVariable("name") String name) {
        return new ResponseEntity<>(farmerService.findByName(name), HttpStatus.OK);
    }
}
