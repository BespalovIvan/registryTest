package com.respak.registryTest.service.impl;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.entity.Area;
import com.respak.registryTest.exception.AreaExistsException;
import com.respak.registryTest.exception.AreaNotFoundException;
import com.respak.registryTest.repository.AreaRepository;
import com.respak.registryTest.service.AreaService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;

    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Transactional
    @Override
    public void addArea(AreaDto areaDto) {
        Optional<Area> optionalByAreaCodeAndName = areaRepository
                .findByAreaCodeAndNameAndIsArchiveFalse(areaDto.getAreaCode(), areaDto.getName());
        if (optionalByAreaCodeAndName.isPresent()) {
            throw new AreaExistsException("Area exists");
        }
        areaRepository.save(new Area(areaDto.getName(), areaDto.getAreaCode(), false));
    }

    @Override
    public Area findById(UUID areaId) {
        return areaRepository.findById(areaId)
                .orElseThrow(() -> new AreaNotFoundException("Active area with id not found!"));
    }

    @Transactional
    @Override
    public List<AreaDto> findAllActiveAreas() {
        List<Area> allAreas = areaRepository.findByIsArchiveFalse();
        if (allAreas.isEmpty()) {
            throw new AreaNotFoundException("Active Area not found!");
        }
        List<AreaDto> areaDtos = new ArrayList<>();
        for (Area area : allAreas) {
            areaDtos.add(new AreaDto(area.getName(), area.getAreaCode()));
        }
        return areaDtos;
    }

    @Override
    public AreaDto findByName(String name) {
        Area area = areaRepository
                .findByNameAndIsArchiveFalse(name)
                .orElseThrow(() -> new AreaNotFoundException("Active Area with name not found!"));
        return new AreaDto(area.getName(), area.getAreaCode());
    }

    @Override
    public AreaDto findByAreaCode(Long areaCode) {

        Area area = areaRepository
                .findByAreaCodeAndIsArchiveFalse(areaCode)
                .orElseThrow(() -> new AreaNotFoundException("Active area with area code not found!"));
        return new AreaDto(area.getName(), area.getAreaCode());
    }

    @Transactional
    @Override
    public void updateArea(AreaDto areaDto) {
        areaRepository.updateArea(areaDto.getName(), areaDto.getAreaCode(), areaDto.getAreaId());
    }

    @Transactional
    @Override
    public void toArchive(AreaDto areaDto) {
        areaRepository.toArchive(areaDto.getAreaId());
    }
}
