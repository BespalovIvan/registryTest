package com.respak.registryTest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.respak.registryTest.entity.Area;
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
    @JsonIgnore
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
    private AreaDto registrationArea;
    @NotNull
    private Set<AreaDto> cropFieldsArea;
    @NotNull
    private LocalDate registrationDate;

    public FarmerDto(String organizationName, String organizationalAndLegalForm, Long inn, Long kpp, Long ogrn,
                     AreaDto registrationArea, Set<AreaDto> cropFieldsArea, LocalDate registrationDate) {
        this.organizationName = organizationName;
        this.organizationalAndLegalForm = organizationalAndLegalForm;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.registrationArea = registrationArea;
        this.cropFieldsArea = cropFieldsArea;
        this.registrationDate = registrationDate;
    }
}
