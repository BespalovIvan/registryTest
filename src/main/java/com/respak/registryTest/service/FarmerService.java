package com.respak.registryTest.service;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.dto.FarmerDto;

import java.util.List;

public interface FarmerService {
    void addFarmer(FarmerDto farmerDto);

    List<FarmerDto> findAllFarmers();

    void toArchive(FarmerDto farmerDto);
    FarmerDto findByName(String organizationName);
}
