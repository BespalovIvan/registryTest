package com.respak.registryTest.controller;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.service.AreaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaController {
    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping("")
    public ResponseEntity<String> createArea(@Validated @RequestBody AreaDto areaDto) {
        areaService.addArea(areaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<AreaDto>> findAllArea() {
        return new ResponseEntity<>(areaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AreaDto> findByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(areaService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/areacode/{areaCode}")
    public ResponseEntity<AreaDto> findByAreaCode(@PathVariable("areaCode") Long areaCode) {
        return new ResponseEntity<>(areaService.findByAreaCode(areaCode), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<String> updateArea(@RequestBody AreaDto areaDto) {
        areaService.updateArea(areaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/archive")
    public ResponseEntity<String> makeArchival(@RequestBody AreaDto areaDto) {
        areaService.toArchive(areaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
