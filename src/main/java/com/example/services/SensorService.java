package com.example.services;

import java.util.List;
import java.util.Optional;

import com.example.models.Sensor;
import com.example.models.SensorType;
import com.example.models.Unit;
import com.example.payload.SensorDto;

public interface SensorService {

	Optional<Sensor> getById(Long id);

	List<Sensor> getAllSensors();

	List<SensorType> getAllSensorTypes();

	List<Unit> getAllUnitForSensorType(Long sensorTypeId);

	Long add(SensorDto sensorDto);

	Long edit(Long id, SensorDto sensorDto);

	void remove(Long id);

	List<Sensor> findByText(String text);

}
