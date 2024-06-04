package com.respak.registryTest.service;

import com.respak.registryTest.dto.FarmerDto;

import java.util.List;
import java.util.UUID;

public interface FarmerService {
    void addFarmer(FarmerDto farmerDto);

    List<FarmerDto> findAllActiveFarmers();

    void toArchive(FarmerDto farmerDto);

    void reArchive(FarmerDto farmerDto);

    FarmerDto findActiveFarmerByOrganizationName(String organizationName);

    void registerFarmer(UUID farmerId, UUID areaId);

    void addCropFieldToFarmer(UUID farmerId, UUID areaId);

    FarmerDto findActiveFarmerByInn(Long inn);

    List<FarmerDto> findActiveFarmerByRegistrationArea(UUID areaId);

    List<FarmerDto> findByRegistrationDate(String registrationDate);

    List<FarmerDto> findByIsArchive(Boolean isArchive);

    void updateFarmer(FarmerDto farmerDto);
}
