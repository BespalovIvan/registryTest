package com.respak.registryTest.repository;

import com.respak.registryTest.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, UUID> {
    @Modifying
    @Query("UPDATE Farmer SET isArchive = true WHERE id = :farmer_id ")
    void toArchive(@Param("farmer_id") UUID farmerId);

    Optional<Farmer> findByOrganizationNameAndIsArchiveFalse(String organizationName);

    List<Farmer> findByIsArchiveFalse();


}


