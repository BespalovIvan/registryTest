package com.respak.registryTest.repository;

import com.respak.registryTest.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, UUID> {
     Area findByName(String name);
     Area findByAreaCode(Long areaCode);
}
