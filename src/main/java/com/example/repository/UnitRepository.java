package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
	
	List<Unit> findAll();
	
	Optional<Unit> findById(Long id);
	
	Optional<Unit> findByName(String name);

	List<Unit> findAllBySensorTypeId(Long id);

}
