package com.respak.registryTest.service.impl;

import com.respak.registryTest.dto.AreaDto;
import com.respak.registryTest.dto.FarmerDto;
import com.respak.registryTest.entity.Area;
import com.respak.registryTest.entity.Farmer;
import com.respak.registryTest.repository.AreaRepository;
import com.respak.registryTest.repository.FarmerRepository;
import com.respak.registryTest.service.FarmerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FarmerServiceImpl implements FarmerService {
    private final FarmerRepository farmerRepository;
    private final AreaRepository areaRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository, AreaRepository areaRepository) {
        this.farmerRepository = farmerRepository;
        this.areaRepository = areaRepository;
    }

    @Transactional
    @Override
    public void addFarmer(FarmerDto farmerDto) {
        farmerRepository.save(new Farmer(farmerDto.getOgrn(), farmerDto.getKpp(), farmerDto.getInn(),
                farmerDto.getOrganizationalAndLegalForm(), farmerDto.getOrganizationName(), farmerDto.getFarmerId(),
                farmerDto.getRegistrationDate(), false));
    }

    @Transactional
    @Override
    public List<FarmerDto> findAllActiveFarmers() {
        List<Farmer> farmers = farmerRepository.findByIsArchiveFalse();
        List<FarmerDto> farmerDtoList = new ArrayList<>();
        for (Farmer farmer : farmers) {
            AreaDto registrationAreaDto = new AreaDto(farmer.getRegistrationArea().getName(),
                    farmer.getRegistrationArea().getAreaCode());
            Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
            Set<AreaDto> cropFieldsAreaDtoSet = new HashSet<>();
            cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new AreaDto(area.getName(),
                    area.getAreaCode())));
            farmerDtoList.add(new FarmerDto(farmer.getFarmerId(), farmer.getOrganizationName(),
                    farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                    farmer.getOgrn(), registrationAreaDto,
                    cropFieldsAreaDtoSet, farmer.getRegistrationDate()));
        }
        return farmerDtoList;
    }

    @Transactional
    @Override
    public void toArchive(FarmerDto farmerDto) {
        farmerRepository.toArchive(farmerDto.getFarmerId());
    }

    @Transactional
    @Override
    public FarmerDto findActiveFarmerByOrganizationName(String organizationName) {
        Optional<Farmer> optionalFarmer = farmerRepository.
                findByOrganizationNameAndIsArchiveFalse(organizationName);
        Farmer farmer = optionalFarmer.orElseThrow(() -> new RuntimeException("Active farmers with name not found"));
        AreaDto registrationAreaDto = new AreaDto(farmer.getRegistrationArea().getName(),
                farmer.getRegistrationArea().getAreaCode());
        Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
        Set<AreaDto> cropFieldsAreaDtoSet = new HashSet<>();
        cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new AreaDto(area.getName(),
                area.getAreaCode())));
        return new FarmerDto(farmer.getOrganizationName(),
                farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                farmer.getOgrn(), registrationAreaDto,
                cropFieldsAreaDtoSet, farmer.getRegistrationDate());
    }

    @Transactional
    @Override
    public void registerFarmer(UUID farmerId, UUID areaId) {
        Optional<Farmer> optionalFarmer = farmerRepository.findById(farmerId);
        Optional<Area> registrationArea = areaRepository.findById(areaId);
        registrationArea.ifPresent((area) -> optionalFarmer.ifPresent((farmer) -> {
                    farmer.setRegistrationArea(area);
                    farmerRepository.save(farmer);
                }
        ));

    }
}
