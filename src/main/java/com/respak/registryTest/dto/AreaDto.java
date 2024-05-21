package com.respak.registryTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDto {
    @NotNull
    private UUID areaId;
    @NotEmpty
    @Column(name = "name")
    private String name;
    @Column(name = "area_code")
    private Long areaCode;

    public AreaDto(String name, Long areaCode) {
        this.name = name;
        this.areaCode = areaCode;
    }
}
