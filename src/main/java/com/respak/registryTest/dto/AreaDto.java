package com.respak.registryTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AreaDto {
    @NotEmpty
    @Column(name = "name")
    private String name;
    @Column(name = "area_code")
    private Long areaCode;
}
