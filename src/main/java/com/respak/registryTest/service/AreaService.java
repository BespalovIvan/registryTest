package com.respak.registryTest.service;


import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.entity.Area;

import java.util.List;
import java.util.UUID;

public interface AreaService {
    void addArea(AreaDto areaDto);

    Area findById(UUID areaId);

    List<AreaDto> findAll();

    AreaDto findByName(String name);

    AreaDto findByAreaCode(Long areaCode);

    void updateArea(AreaDto areaDto);

    void toArchive(AreaDto areaDto);
}
