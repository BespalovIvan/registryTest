package com.respak.registryTest.service.impl;

import com.respak.registryTest.dto.FarmerAreaDto;
import com.respak.registryTest.dto.FarmerDto;
import com.respak.registryTest.entity.Area;
import com.respak.registryTest.entity.Farmer;
import com.respak.registryTest.exception.AreaNotFoundException;
import com.respak.registryTest.exception.FarmerExistsException;
import com.respak.registryTest.exception.FarmerNotFoundException;
import com.respak.registryTest.exception.IncorrectDateFormatException;
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
        Optional<Farmer> optionalByOrganizationName = farmerRepository
                .findByOrganizationNameAndIsArchiveFalse(farmerDto.getOrganizationName());
        Optional<Farmer> optionalByInn = farmerRepository.findByInnAndIsArchiveFalse(farmerDto.getInn());
        Optional<Farmer> optionalByOgrn = farmerRepository.findByOgrn(farmerDto.getOgrn());
        if (optionalByOrganizationName.isPresent() || optionalByOgrn.isPresent() || optionalByInn.isPresent()) {
            throw new FarmerExistsException("Farmer exists!");
        }
        farmerRepository.save(new Farmer(farmerDto.getOgrn(), farmerDto.getKpp(), farmerDto.getInn(),
                farmerDto.getOrganizationalAndLegalForm(), farmerDto.getOrganizationName(), farmerDto.getFarmerId(),
                farmerDto.getRegistrationDate(), false));
    }

    @Transactional
    @Override
    public List<FarmerDto> findAllActiveFarmers() {
        List<Farmer> farmers = farmerRepository.findByIsArchiveFalse();
        if (farmers.isEmpty()) {
            throw new FarmerNotFoundException("Farmers not found!");
        }
        List<FarmerDto> farmerDtoList = new ArrayList<>();
        for (Farmer farmer : farmers) {
            Area areaRegistration = farmer.getRegistrationArea();
            FarmerAreaDto registrationAreaDto;
            if (areaRegistration == null) {
                registrationAreaDto = new FarmerAreaDto("Not registered");
            } else {
                registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
            }
            Set<Area> cropFieldsAreaSet = farmer.getCropFieldsArea();
            Set<FarmerAreaDto> cropFieldsAreaDtoSet = new HashSet<>();
            cropFieldsAreaSet.forEach((area) -> cropFieldsAreaDtoSet.add(new FarmerAreaDto(area.getName())));
            farmerDtoList.add(new FarmerDto(farmer.getFarmerId(),
                    farmer.getOrganizationName(),
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
        Farmer farmer = optionalFarmer
                .orElseThrow(() -> new FarmerNotFoundException("Active farmers with name not found"));
        Area areaRegistration = farmer.getRegistrationArea();
        FarmerAreaDto registrationAreaDto;
        if (areaRegistration == null) {
            registrationAreaDto = new FarmerAreaDto("Not registered");
        } else {
            registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
        }
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
        Farmer farmer = optionalFarmer.orElseThrow(() -> new FarmerNotFoundException("Farmer with id not found!"));
        Area area = registrationArea.orElseThrow(() -> new AreaNotFoundException("Area with id not found!"));
        farmer.setRegistrationArea(area);
        farmerRepository.save(farmer);
    }

    @Transactional
    @Override
    public void addCropFieldToFarmer(UUID farmerId, UUID areaId) {
        Optional<Farmer> optionalFarmer = farmerRepository.findById(farmerId);
        Optional<Area> cropFieldArea = areaRepository.findById(areaId);
        Farmer farmer = optionalFarmer.orElseThrow(() -> new FarmerNotFoundException("Farmer with id not found!"));
        Area area = cropFieldArea.orElseThrow(() -> new AreaNotFoundException("Area with id not found!"));
        if (farmer.getCropFieldsArea() != null) {
            farmer.getCropFieldsArea().add(area);
        } else {
            Set<Area> cropFieldsArea = new HashSet<>();
            cropFieldsArea.add(area);
            farmer.setCropFieldsArea(cropFieldsArea);
        }
        farmerRepository.save(farmer);
    }


    @Transactional
    @Override
    public FarmerDto findActiveFarmerByInn(Long inn) {
        Optional<Farmer> optionalFarmer = farmerRepository.
                findByInnAndIsArchiveFalse(inn);
        Farmer farmer = optionalFarmer
                .orElseThrow(() -> new FarmerNotFoundException("Active farmers with inn not found"));
        Area areaRegistration = farmer.getRegistrationArea();
        FarmerAreaDto registrationAreaDto;
        if (areaRegistration == null) {
            registrationAreaDto = new FarmerAreaDto("Not registered");
        } else {
            registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
        }
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
        Area areaById = optionalArea.orElseThrow(() -> new AreaNotFoundException("Area with id not found!"));
        List<Farmer> farmers = farmerRepository.findByRegistrationAreaAndIsArchiveFalse(areaById);
        if(farmers.isEmpty()){
            throw new FarmerNotFoundException("Farmer with registration this area not found!");
        }
        List<FarmerDto> farmerDtoList = new ArrayList<>();
        for (Farmer farmer : farmers) {
            Area areaRegistration = farmer.getRegistrationArea();
            FarmerAreaDto registrationAreaDto;
            if (areaRegistration == null) {
                registrationAreaDto = new FarmerAreaDto("Not registered");
            } else {
                registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
            }
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
                Area areaRegistration = farmer.getRegistrationArea();
                FarmerAreaDto registrationAreaDto;
                if (areaRegistration == null) {
                    registrationAreaDto = new FarmerAreaDto("Not registered");
                } else {
                    registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
                }
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
            throw new IncorrectDateFormatException("Incorrect date format");
        }
        throw new FarmerNotFoundException("Active Farmer with registration date not found");
    }

    @Transactional
    @Override
    public List<FarmerDto> findByIsArchive(Boolean isArchive) {
        List<Farmer> farmers = farmerRepository.findByIsArchive(isArchive);
        if (farmers.isEmpty()) {
            throw new FarmerNotFoundException("Farmer not found!");
        }
        List<FarmerDto> farmerDtoList = new ArrayList<>();
        for (Farmer farmer : farmers) {
            Area areaRegistration = farmer.getRegistrationArea();
            FarmerAreaDto registrationAreaDto;
            if (areaRegistration == null) {
                registrationAreaDto = new FarmerAreaDto("Not registered");
            } else {
                registrationAreaDto = new FarmerAreaDto(farmer.getRegistrationArea().getName());
            }
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
    public void updateFarmer(FarmerDto farmerDto) {
        Optional<Farmer> optionalFarmer = farmerRepository.findById(farmerDto.getFarmerId());
        if (optionalFarmer.isPresent()) {
            Farmer farmer = optionalFarmer.get();
            farmer.setOrganizationName(farmerDto.getOrganizationName());
            farmer.setOrganizationalAndLegalForm(farmerDto.getOrganizationalAndLegalForm());
            farmer.setInn(farmerDto.getInn());
            farmer.setKpp(farmerDto.getKpp());
            farmer.setOgrn(farmerDto.getOgrn());
            farmer.setRegistrationDate(farmerDto.getRegistrationDate());
            farmerRepository.save(farmer);
        } else {
            throw new FarmerNotFoundException("Farmer with id not found! ");
        }
    }
}
