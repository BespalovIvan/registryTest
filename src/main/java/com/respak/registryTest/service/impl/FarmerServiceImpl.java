package com.respak.registryTest.service.impl;

import com.respak.registryTest.dto.FarmerAreaDto;
import com.respak.registryTest.dto.FarmerDto;
import com.respak.registryTest.entity.Area;
import com.respak.registryTest.entity.Farmer;
import com.respak.registryTest.repository.AreaRepository;
import com.respak.registryTest.repository.FarmerRepository;
import com.respak.registryTest.service.FarmerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            FarmerAreaDto registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
            Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
            Set<FarmerAreaDto> cropFieldsAreaDtoSet = new HashSet<>();
            cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new FarmerAreaDto(area.getName())));
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
    public void reArchive(FarmerDto farmerDto) {
        farmerRepository.reArchive(farmerDto.getFarmerId());
    }

    @Transactional
    @Override
    public FarmerDto findActiveFarmerByOrganizationName(String organizationName) {
        Optional<Farmer> optionalFarmer = farmerRepository.
                findByOrganizationNameAndIsArchiveFalse(organizationName);
        Farmer farmer = optionalFarmer.orElseThrow(() -> new RuntimeException("Active farmers with name not found"));
        FarmerAreaDto registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
        Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
        Set<FarmerAreaDto> cropFieldsAreaDtoSet = new HashSet<>();
        cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new FarmerAreaDto(area.getName())));
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
        }));
    }

    @Transactional
    @Override
    public void addCropFieldToFarmer(UUID farmerId, UUID areaId) {
        Optional<Farmer> optionalFarmer = farmerRepository.findById(farmerId);
        Optional<Area> cropFieldArea = areaRepository.findById(areaId);
        cropFieldArea.ifPresent((area) -> optionalFarmer.ifPresent((farmer) -> {
            if (farmer.getCropFieldsArea() != null) {
                farmer.getCropFieldsArea().add(area);
            } else {
                Set<Area> cropFieldsArea = new HashSet<>();
                cropFieldsArea.add(area);
                farmer.setCropFieldsArea(cropFieldsArea);
            }
            farmerRepository.save(farmer);
        }));
    }

    @Transactional
    @Override
    public FarmerDto findActiveFarmerByInn(Long inn) {
        Optional<Farmer> optionalFarmer = farmerRepository.
                findByInnAndIsArchiveFalse(inn);
        Farmer farmer = optionalFarmer.orElseThrow(() -> new RuntimeException("Active farmers with inn not found"));
        FarmerAreaDto registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
        Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
        Set<FarmerAreaDto> cropFieldsAreaDtoSet = new HashSet<>();
        cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new FarmerAreaDto(area.getName())));
        return new FarmerDto(farmer.getOrganizationName(),
                farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                farmer.getOgrn(), registrationAreaDto,
                cropFieldsAreaDtoSet, farmer.getRegistrationDate());
    }

    @Transactional
    @Override
    public List<FarmerDto> findActiveFarmerByRegistrationArea(UUID areaId) {
        Optional<Area> optionalArea = areaRepository.findById(areaId);
        List<Farmer> farmers = farmerRepository.findByRegistrationAreaAndIsArchiveFalse(
                optionalArea.orElseThrow(RuntimeException::new));
        List<FarmerDto> farmerDtoList = new ArrayList<>();
        for (Farmer farmer : farmers) {
            FarmerAreaDto registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
            Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
            Set<FarmerAreaDto> cropFieldsAreaDtoSet = new HashSet<>();
            cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new FarmerAreaDto(area.getName())));
            farmerDtoList.add(new FarmerDto(farmer.getFarmerId(), farmer.getOrganizationName(),
                    farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                    farmer.getOgrn(), registrationAreaDto,
                    cropFieldsAreaDtoSet, farmer.getRegistrationDate()));
        }
        return farmerDtoList;
    }

    @Transactional
    @Override
    public List<FarmerDto> findByRegistrationDate(String registrationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(registrationDate, formatter);
            List<Farmer> farmers = farmerRepository.findByRegistrationDateAndIsArchiveFalse(date);
            List<FarmerDto> farmerDtoList = new ArrayList<>();
            for (Farmer farmer : farmers) {
                FarmerAreaDto registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
                Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
                Set<FarmerAreaDto> cropFieldsAreaDtoSet = new HashSet<>();
                cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new FarmerAreaDto(area.getName())));
                farmerDtoList.add(new FarmerDto(farmer.getFarmerId(), farmer.getOrganizationName(),
                        farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                        farmer.getOgrn(), registrationAreaDto,
                        cropFieldsAreaDtoSet, farmer.getRegistrationDate()));
                return farmerDtoList;
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Incorrect date format");
        }
        throw new RuntimeException("Active Farmer with registration date not found");
    }

    @Transactional
    @Override
    public List<FarmerDto> findByIsArchive(Boolean isArchive) {
        List<Farmer> farmers = farmerRepository.findByIsArchive(isArchive);
        List<FarmerDto> farmerDtoList = new ArrayList<>();
        for (Farmer farmer : farmers) {
            FarmerAreaDto registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
            Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
            Set<FarmerAreaDto> cropFieldsAreaDtoSet = new HashSet<>();
            cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new FarmerAreaDto(area.getName())));
            farmerDtoList.add(new FarmerDto(farmer.getFarmerId(), farmer.getOrganizationName(),
                    farmer.getOrganizationalAndLegalForm(), farmer.getInn(), farmer.getKpp(),
                    farmer.getOgrn(), registrationAreaDto,
                    cropFieldsAreaDtoSet, farmer.getRegistrationDate()));
        }
        return farmerDtoList;
    }
}
