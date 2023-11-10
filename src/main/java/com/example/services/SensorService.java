package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.models.Sensor;
import com.example.models.SensorType;
import com.example.models.Unit;
import com.example.payload.SensorDto;

public interface SensorService {

	Optional<Sensor> getById(Long id);

	Page<Sensor> getAllSensors(Integer pageNumber, Integer pageSize);

	List<SensorType> getAllSensorTypes();

	List<Unit> getAllUnitForSensorType(Long sensorTypeId);

	Long add(SensorDto sensorDto);

	Long edit(Long id, SensorDto sensorDto);

	void remove(Long id);

	Page<Sensor> findByText(Integer pageNumber, Integer pageSize, String text);

}
