package com.respak.registryTest.repository;

import com.respak.registryTest.entity.Area;
import com.respak.registryTest.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, UUID> {
    @Modifying
    @Query("UPDATE Farmer SET isArchive = true WHERE id = :farmer_id ")
    void toArchive(@Param("farmer_id") UUID farmerId);

    @Modifying
    @Query("UPDATE Farmer SET isArchive = false WHERE id = :farmer_id ")
    void reArchive(@Param("farmer_id") UUID farmerId);
    Optional<Farmer> findByOrganizationNameAndInnAndOgrnAndIsArchiveFalse(String organizationName,Long inn, Long ogrn);

    Optional<Farmer> findByOrganizationNameAndIsArchiveFalse(String organizationName);

    List<Farmer> findByIsArchiveFalse();

    Optional<Farmer> findByInnAndIsArchiveFalse(Long inn);

    List<Farmer> findByRegistrationAreaAndIsArchiveFalse(Area registrationArea);

    List<Farmer> findByRegistrationDateAndIsArchiveFalse(LocalDate registrationDate);

    List<Farmer> findByIsArchive(Boolean isArchive);
    Optional<Farmer> findByOgrn(Long ogrn);
}


