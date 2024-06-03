package com.respak.registryTest.service.impl;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.entity.Area;
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

    @Override
    public void addArea(AreaDto areaDto) {
        areaRepository.save(new Area(areaDto.getName(), areaDto.getAreaCode(), false));
    }

    @Override
    public Area findById(UUID areaId) {
        return areaRepository.findById(areaId).orElseThrow(() -> new RuntimeException("Active area with id not found"));
    }

    @Transactional
    @Override
    public List<AreaDto> findAllActiveAreas() {
        List<Area> allAreas = areaRepository.findByIsArchiveFalse();
        List<AreaDto> areaDtos = new ArrayList<>();
        for (Area area : allAreas) {
            areaDtos.add(new AreaDto(area.getName(), area.getAreaCode()));
        }
        return areaDtos;
    }

    @Override
    public AreaDto findByName(String name) {
        Optional<Area> optionalArea = areaRepository.findByNameAndIsArchiveFalse(name);
        Area area = optionalArea.orElseThrow(RuntimeException::new);
        return new AreaDto(area.getName(), area.getAreaCode());
    }

    @Override
    public AreaDto findByAreaCode(Long areaCode) {
        Optional<Area> optionalArea = areaRepository.findByAreaCodeAndIsArchiveFalse(areaCode);
        Area area = optionalArea.orElseThrow(RuntimeException::new);
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
