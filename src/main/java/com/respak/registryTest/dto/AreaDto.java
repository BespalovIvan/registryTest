package com.respak.registryTest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AreaDto {
    private UUID areaId;
    @NotEmpty
    private String name;
    @NotNull
    private Long areaCode;

    public AreaDto(String name, Long areaCode) {
        this.name = name;
        this.areaCode = areaCode;
    }

    public AreaDto(UUID areaId) {
        this.areaId = areaId;
    }

    public AreaDto(UUID areaId, String name, Long areaCode) {
        this.areaId = areaId;
        this.name = name;
        this.areaCode = areaCode;
    }
}
