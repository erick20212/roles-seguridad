package com.example.security.repository;

import com.example.security.entity.Plan_Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Plan_Carrera_Repository extends JpaRepository<Plan_Carrera, Long> {
}
