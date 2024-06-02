package com.respak.registryTest.service;

import com.respak.registryTest.dto.FarmerDto;

import java.util.List;
import java.util.UUID;

public interface FarmerService {
    void addFarmer(FarmerDto farmerDto);

    List<FarmerDto> findAllActiveFarmers();

    void toArchive(FarmerDto farmerDto);

    FarmerDto findActiveFarmerByOrganizationName(String organizationName);
    void registerFarmer (UUID farmerId,UUID areaId);
}
