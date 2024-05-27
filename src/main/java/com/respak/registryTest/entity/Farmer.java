package com.respak.registryTest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;


@Table(name = "farmers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "farmer_id")
    private UUID farmerId;
    @NotEmpty
    @Column(name = "organization_name")
    private String organizationName;
    @Column(name = "organizational_and_legal_form")
    private String organizationalAndLegalForm;
    @Column(name = "inn")
    private Long inn;
    @Column(name = "kpp")
    private Long kpp;
    @Column(name = "ogrn")
    private Long ogrn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area registrationArea;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "farmers_crop_fields_area",
            joinColumns = {@JoinColumn(name = "farmer_id")},
            inverseJoinColumns = {@JoinColumn(name = "area_id")})
    private Set<Area> cropFieldsArea;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column(name = "is_archive")
    private Boolean isArchive;

    public Farmer(String organizationName, String organizationalAndLegalForm, Long inn, Long kpp, Long ogrn,
                  Area registrationArea, Set<Area> cropFieldsArea, LocalDate registrationDate, Boolean isArchive) {
        this.organizationName = organizationName;
        this.organizationalAndLegalForm = organizationalAndLegalForm;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.registrationArea = registrationArea;
        this.cropFieldsArea = cropFieldsArea;
        this.registrationDate = registrationDate;
        this.isArchive = isArchive;
    }

    public Farmer(UUID farmerId) {
        this.farmerId = farmerId;
    }
}
