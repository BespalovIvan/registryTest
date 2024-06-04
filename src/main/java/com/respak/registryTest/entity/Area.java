package com.respak.registryTest.entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;


@Table(name = "areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "area_id")
    private UUID areaId;
    @NotEmpty(message = "name cannot be empty")
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @Column(name = "area_code",nullable = false,unique = true)
    private Long areaCode;
    @Column(name = "is_archive",nullable = false)
    private Boolean isArchive;
    @OneToMany(mappedBy = "registrationArea",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Farmer> registrationFarmers;
    @ManyToMany(mappedBy = "cropFieldsArea")
    private Set<Farmer> cropFieldsAreaFarmers;

    public Area(String name, Long areaCode) {
        this.name = name;
        this.areaCode = areaCode;
    }

    public Area(String name, Long areaCode, Boolean isArchive) {
        this.name = name;
        this.areaCode = areaCode;
        this.isArchive = isArchive;
    }

    public Area(UUID areaId) {
        this.areaId = areaId;
    }
}
