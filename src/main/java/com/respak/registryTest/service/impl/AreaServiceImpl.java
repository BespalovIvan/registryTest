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
    public UUID addArea(AreaDto areaDto) {
        return areaRepository.save(new Area(areaDto.getName(), areaDto.getAreaCode(),false)).getAreaId();
    }

    @Override
    public List<AreaDto> findAll() {
        List<Area> allAreas = areaRepository.findAll();
        if(allAreas.isEmpty()){
            throw new RuntimeException("Active areas not found");
        }
        List<AreaDto> areaDtos = new ArrayList<>();
        for (Area area : allAreas) {
            if (!area.getIsArchive()) {
                areaDtos.add(new AreaDto(area.getName(), area.getAreaCode()));
            }
        }

        return areaDtos;
    }

    @Override
    public AreaDto findByName(String name) {
        Optional<Area> optionalArea = areaRepository.findByName(name);
        Area area = optionalArea.orElseThrow(RuntimeException::new);
        if (!area.getIsArchive()) {
            return new AreaDto(area.getName(), area.getAreaCode());
        } else {
            throw new RuntimeException("Active area with name not found");
        }
    }

    @Override
    public AreaDto findByAreaCode(Long areaCode) {
        Optional<Area> optionalArea = areaRepository.findByAreaCode(areaCode);
        Area area = optionalArea.orElseThrow(RuntimeException::new);
        if (!area.getIsArchive()) {
            return new AreaDto(area.getName(), area.getAreaCode());
        } else {
            throw new RuntimeException("Active area with area code not found");
        }
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
