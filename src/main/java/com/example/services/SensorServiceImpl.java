package com.example.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.Sensor;
import com.example.models.SensorType;
import com.example.models.Unit;
import com.example.payload.SensorDto;
import com.example.repository.SensorRepository;
import com.example.repository.SensorTypeRepository;
import com.example.repository.UnitRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

	private static final String NOT_EXIST_UNIT_ERR_MSG_FORMAT = "Unit with name %s not exist";
	private static final String NOT_EXIST_SENSOR_ERR_MSG_FORMAT = "Sensor with id %d not exist";

	private final SensorRepository sensorRepository;
	private final UnitRepository unitRepository;
	private final SensorTypeRepository sensorTypeRepository;

	@Override
	public Optional<Sensor> getById(Long id) {
		return sensorRepository.findById(id);
	}

	@Override
	public List<Sensor> getAllSensors() {
		return sensorRepository.findAll();
	}

	@Override
	public List<SensorType> getAllSensorTypes() {
		return sensorTypeRepository.findAll();
	}

	@Override
	public List<Unit> getAllUnitForSensorType(Long sensorTypeId) {
		return unitRepository.findAllBySensorTypeId(sensorTypeId);
	}

	@Override
	@Transactional
	public Long add(SensorDto sensorDto) {
		Optional<Unit> newUnit = unitRepository.findByName(sensorDto.getUnit());
		if (newUnit.isPresent()) {
			Sensor newSensor = new Sensor();
			newSensor.setName(sensorDto.getName());
			newSensor.setModel(sensorDto.getModel());
			newSensor.setDescription(sensorDto.getDescription());
			newSensor.setLocation(sensorDto.getLocation());
			newSensor.setUnit(newUnit.get());
			newSensor.setRangeFrom(sensorDto.getRangeFrom());
			newSensor.setRangeTo(sensorDto.getRangeTo());
			return sensorRepository.save(newSensor).getId();
		} else {
			log.info("Unit not found in DB");
			throw new EntityNotFoundException(String.format(NOT_EXIST_UNIT_ERR_MSG_FORMAT, sensorDto.getUnit()));
		}
	}

	@Override
	@Transactional
	public Long edit(Long id, SensorDto sensorDto) {
		Optional<Sensor> editedSensor = sensorRepository.findById(id);
		Optional<Unit> newUnit = unitRepository.findByName(sensorDto.getUnit());
		if (newUnit.isPresent()) {
			if (editedSensor.isPresent()) {
				Sensor newSensor = editedSensor.get();
				newSensor.setName(sensorDto.getName());
				newSensor.setModel(sensorDto.getModel());
				newSensor.setDescription(sensorDto.getDescription());
				newSensor.setLocation(sensorDto.getLocation());
				newSensor.setUnit(newUnit.get());
				newSensor.setRangeFrom(sensorDto.getRangeFrom());
				newSensor.setRangeTo(sensorDto.getRangeTo());
				return id = sensorRepository.save(newSensor).getId();
			} else {
				log.info("Sensor not found in DB");
				throw new EntityNotFoundException(String.format(NOT_EXIST_SENSOR_ERR_MSG_FORMAT, id));
			}
		} else {
			log.info("Unit not found in DB");
			throw new EntityNotFoundException(String.format(NOT_EXIST_UNIT_ERR_MSG_FORMAT, sensorDto.getUnit()));
		}
	}

	@Override
	@Transactional
	public void remove(Long id) {
		sensorRepository.deleteById(id);
	}

	@Override
	public List<Sensor> findByText(String text) {
		return sensorRepository.findAllByText(text);
	}
}
