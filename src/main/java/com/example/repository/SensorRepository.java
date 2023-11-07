package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.models.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
	
	List<Sensor> findAll();
	
	Optional<Sensor> findById(Long id);
	
	@Query(value = 
			"SELECT * FROM public.sensors s "
			+ "LEFT JOIN public.units u ON u.id = s.unit_id "
			+ "LEFT JOIN public.sensor_types st ON u.sensor_type_id = st.id "
			+ "WHERE (s.name LIKE (CONCAT('%', ?1,'%')))"
			+ "	OR (s.location LIKE (CONCAT('%', ?1,'%')))"
			+ "	OR (s.model_name LIKE (CONCAT('%', ?1,'%')))"
			+ "	OR (s.description LIKE (CONCAT('%', ?1,'%')))"
			+ "	OR (st.name LIKE (CONCAT('%', ?1,'%')))"
			+ "	OR (u.name LIKE (CONCAT('%', ?1,'%')))"
			, nativeQuery = true)
	List<Sensor> findAllByText(String search);

}
