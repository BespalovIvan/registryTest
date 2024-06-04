package com.respak.registryTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDto {

    private UUID farmerId;
    @NotEmpty
    private String organizationName;
    @NotNull
    private String organizationalAndLegalForm;
    @NotNull
    private Long inn;
    @NotNull
    private Long kpp;
    @NotNull
    private Long ogrn;
    @NotNull
    private FarmerAreaDto registrationArea;
    @NotNull
    private Set<FarmerAreaDto> cropFieldsArea;
    @NotNull
    private LocalDate registrationDate;

    public FarmerDto(String organizationName, String organizationalAndLegalForm, Long inn, Long kpp, Long ogrn,
                     FarmerAreaDto registrationArea, Set<FarmerAreaDto> cropFieldsArea, LocalDate registrationDate) {
        this.organizationName = organizationName;
        this.organizationalAndLegalForm = organizationalAndLegalForm;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.registrationArea = registrationArea;
        this.cropFieldsArea = cropFieldsArea;
        this.registrationDate = registrationDate;
    }

    public FarmerDto(UUID farmerId, String organizationName, String organizationalAndLegalForm,
                     Long inn, Long kpp,
                     Long ogrn, LocalDate registrationDate) {
        this.farmerId = farmerId;
        this.organizationName = organizationName;
        this.organizationalAndLegalForm = organizationalAndLegalForm;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.registrationDate = registrationDate;
    }
}
