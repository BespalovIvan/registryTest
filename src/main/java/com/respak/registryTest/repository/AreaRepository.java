package com.respak.registryTest.repository;

import com.respak.registryTest.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, UUID> {
    Optional<Area> findByName(String name);

    Optional<Area> findByAreaCode(Long areaCode);

    @Modifying
    @Query("UPDATE Area SET name = :name, area_code = :area_code WHERE id = :area_id ")
    void updateArea(@Param("name") String name,@Param("area_code") Long areaCode,@Param("area_id") UUID areaId);

}
