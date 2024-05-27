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
    private UUID registrationAreaId;
    @NotNull
    private Set<UUID> cropFieldsAreaId;
    @NotNull
    private LocalDate registrationDate;

    public FarmerDto(String organizationName, String organizationalAndLegalForm, Long inn, Long kpp, Long ogrn,
                     UUID registrationAreaId, Set<UUID> cropFieldsAreaId, LocalDate registrationDate) {
        this.organizationName = organizationName;
        this.organizationalAndLegalForm = organizationalAndLegalForm;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.registrationAreaId = registrationAreaId;
        this.cropFieldsAreaId = cropFieldsAreaId;
        this.registrationDate = registrationDate;
    }
}