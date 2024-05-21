package com.respak.registryTest.service.impl;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.entity.Area;
import com.respak.registryTest.repository.AreaRepository;
import com.respak.registryTest.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;

    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public UUID addArea(AreaDto areaDto) {
        return areaRepository.save(new Area(areaDto.getName(), areaDto.getAreaCode())).getAreaId();
    }

    @Override
    public List<AreaDto> findAll() {
        List<Area> allAreas = areaRepository.findAll();
        List<AreaDto> areaDtos = new ArrayList<>();
        for (Area area : allAreas) {
            areaDtos.add(new AreaDto(area.getName(), area.getAreaCode()));
        }
        return areaDtos;
    }

    @Override
    public AreaDto findByName(String name) {
        Area area = areaRepository.findByName(name);
        return new AreaDto(area.getName(), area.getAreaCode());
    }

    @Override
    public AreaDto findByAreaCode(Long areaCode) {
        Area area = areaRepository.findByAreaCode(areaCode);
        return new AreaDto(area.getName(), area.getAreaCode());
    }
}
