package com.respak.registryTest.service.impl;

import com.respak.registryTest.dto.FarmerDto;
import com.respak.registryTest.entity.Area;
import com.respak.registryTest.entity.Farmer;
import com.respak.registryTest.repository.FarmerRepository;
import com.respak.registryTest.service.AreaService;
import com.respak.registryTest.service.FarmerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FarmerServiceImpl implements FarmerService {
    private final FarmerRepository farmerRepository;
    private final AreaService areaService;

    public FarmerServiceImpl(FarmerRepository farmerRepository, AreaService areaService) {
        this.farmerRepository = farmerRepository;
        this.areaService = areaService;
    }

    @Override
    public void addFarmer(FarmerDto farmerDto) {
        Area registrationArea = areaService.findById(farmerDto.getRegistrationAreaId());
        Set<UUID> areaIdSet = farmerDto.getCropFieldsAreaId();
        Set<Area> CropAreas = new HashSet<>();
        for (UUID id : areaIdSet) {
            CropAreas.add(areaService.findById(id));
        }
        farmerRepository.save(new Farmer(farmerDto.getOrganizationName(),
                farmerDto.getOrganizationalAndLegalForm(), farmerDto.getInn(), farmerDto.getKpp(),
                farmerDto.getOgrn(), registrationArea, CropAreas,
                farmerDto.getRegistrationDate(), false));
    }

    @Override
    public List<FarmerDto> findAllFarmers() {
        List<Farmer> farmers = farmerRepository.findAll();
        List<FarmerDto> farmerDtoList = new ArrayList<>();
        for (Farmer farmer : farmers) {
            if (!farmer.getIsArchive()) {
                Set<Area> cropAreas = farmer.getCropFieldsArea();
                Set<UUID> cropAreaId = new HashSet<>();
                for (Area area : cropAreas) {
                    cropAreaId.add(area.getAreaId());
                }
                farmerDtoList.add(new FarmerDto(farmer.getFarmerId(), farmer.getOrganizationName(),
                        farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                        farmer.getOgrn(), farmer.getRegistrationArea().getAreaId()
                        , cropAreaId, farmer.getRegistrationDate()));
            }
        }
        return farmerDtoList;
    }

    @Transactional
    @Override
    public void toArchive(FarmerDto farmerDto) {
        farmerRepository.toArchive(farmerDto.getFarmerId());
    }

    @Override
    public FarmerDto findByName(String organizationName) {
        Optional<Farmer> optionalFarmer = farmerRepository.findByOrganizationName(organizationName);
        Farmer farmer = optionalFarmer.orElseThrow(RuntimeException::new);
        if (!farmer.getIsArchive()) {
            Set<Area> cropAreas = farmer.getCropFieldsArea();
            Set<UUID> cropAreaId = new HashSet<>();
            for (Area area : cropAreas) {
                cropAreaId.add(area.getAreaId());
            }
            return new FarmerDto(farmer.getOrganizationName(),
                    farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                    farmer.getOgrn(), farmer.getRegistrationArea().getAreaId()
                    , cropAreaId, farmer.getRegistrationDate());
        } else {
            throw new RuntimeException("Active farmers with name not found");
        }

    }
}
