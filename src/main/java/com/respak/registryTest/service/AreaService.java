package com.respak.registryTest.service;


import com.respak.registryTest.dto.AreaDto;

import java.util.List;
import java.util.UUID;

public interface AreaService {
    UUID addArea(AreaDto areaDto);

    List<AreaDto> findAll();

    AreaDto findByName(String name);

    AreaDto findByAreaCode(Long areaCode);

    void updateArea(AreaDto areaDto);
    void toArchive(AreaDto areaDto);
}
