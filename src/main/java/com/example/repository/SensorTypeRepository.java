package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.SensorType;

public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {

	Optional<SensorType> findById(Long id);
	
	Optional<SensorType> findByName(String name);
	
	List<SensorType> findAll();

}
