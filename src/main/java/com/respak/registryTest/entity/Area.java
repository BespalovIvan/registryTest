package com.respak.registryTest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "areas")
@Data
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "area_id")
    private UUID areaId;
    @NotEmpty(message = "name cannot be empty")
    @Column(name = "name")
    private String name;
    @Column(name = "area_code")
    private Long areaCode;
    @Column(name = "is_archive")
    private Boolean isArchive;
    @OneToMany
    @JoinColumn(name = "registration_farmer_id")
    private Set<Farmer> registrationFarmers;
    @ManyToMany
    @JoinColumn(name = "farmer_id")
    private Set<Farmer> cropFieldsAreaFarmers;

    public Area(String name, Long areaCode) {
        this.name = name;
        this.areaCode = areaCode;
    }
}
