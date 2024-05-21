package com.respak.registryTest.entity;

import com.respak.registryTest.dto.OrganizationalForm;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "farmers")
@Data
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "farmer_id")
    private UUID farmerId;
    @NotEmpty
    @Column(name = "organization_name")
    private String organizationName;
    @Column(name = "organizational_and_legal_form")
    private OrganizationalForm organizationalAndLegalForm;
    @Column(name = "inn")
    private Long INN;
    @Column(name = "kpp")
    private Long KPP;
    @Column(name = "ogrn")
    private Long OGRN;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area registrationArea;
    @ManyToMany
    @JoinColumn(name = "area_id")
    private Set<Area> cropFieldsArea;
}
