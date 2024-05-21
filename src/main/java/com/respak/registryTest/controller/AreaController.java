package com.respak.registryTest.controller;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.service.AreaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AreaController {
    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping("/api/add")
    public ResponseEntity<String> createArea(@Validated @RequestBody AreaDto areaDto) {
        areaService.addArea(areaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/areas")
    public ResponseEntity<List<AreaDto>> findAllArea() {
        return new ResponseEntity<>(areaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/areas/name/{name}")
    public ResponseEntity<AreaDto> findByNameAndAreaCode(@PathVariable("name") String name) {
        return new ResponseEntity<>(areaService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/api/areas/areacode/{areaCode}")
    public ResponseEntity<AreaDto> findByNameAndAreaCode(@PathVariable("areaCode") Long areaCode) {
        return new ResponseEntity<>(areaService.findByAreaCode(areaCode), HttpStatus.OK);
    }
}
