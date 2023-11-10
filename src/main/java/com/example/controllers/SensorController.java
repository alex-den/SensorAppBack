package com.example.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Sensor;
import com.example.models.SensorType;
import com.example.models.Unit;
import com.example.payload.MessageResponse;
import com.example.payload.PageSensorDto;
import com.example.payload.SensorDto;
import com.example.services.SensorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class SensorController {

	private static final String EDIT_MSG_FORMAT = "Sensor with id = %d changed";
	private static final String ADD_MSG_FORMAT = "Sensor added id = %d";
	private static final String DELETE_MSG_FORMAT = "Sensor with id = %d deleted";

	private final SensorService sensorService;

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleException(EntityNotFoundException ex) {
		log.info("Send no content because exception {} ", ex.getMessage());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/sensors")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PageSensorDto> getSensors(
			@RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "4") Integer size){
		log.info("Try to get all sensor data");
		Page<Sensor> sensorsPage = sensorService.getAllSensors(page, size);
		log.info("Found : " + sensorsPage.toString());
		List<SensorDto> sensorsDto = sensorsPage.getContent().stream().map(SensorDto::toDto).collect(Collectors.toList());
		log.info("Found : " + sensorsDto.toString());
		PageSensorDto result = new PageSensorDto(sensorsDto, sensorsPage.getNumber(),sensorsPage.getTotalElements(), sensorsPage.getTotalPages());
		log.info("Found : " + result.toString());
		if (sensorsDto.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/sensor-types")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<SensorType>> getSensorTypes() {
		log.info("Try get all sensor type data");
		List<SensorType> sensorTypes = sensorService.getAllSensorTypes();
		if (sensorTypes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(sensorTypes);
	}

	@GetMapping("/units-for-sensor-type")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Unit>> getUnitsForSensorType(@RequestParam("id") Long id) {
		log.info("Try to get all unit data");
		List<Unit> units = sensorService.getAllUnitForSensorType(id);
		if (units.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(units);
	}

	@GetMapping("/find-sensors")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PageSensorDto> getSensorsByText(
			@RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "4") Integer size,
			@RequestParam("searchText") String searchText) {
		log.info("Try get data with filter {}", searchText);
		Page<Sensor> sensorsPage = sensorService.findByText(page, size, searchText);
		List<SensorDto> sensorsDto = sensorsPage.getContent().stream().map(SensorDto::toDto).collect(Collectors.toList());
		PageSensorDto result = new PageSensorDto(sensorsDto, sensorsPage.getNumber(),sensorsPage.getTotalElements(), sensorsPage.getTotalPages());
		log.info("Get data with filter size = {} ", sensorsDto.size());
			if (sensorsDto.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/sensors/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SensorDto> getSensorById(@PathVariable("id") Long id) {
		log.info("Try get data by {}", id);
		Optional<Sensor> sensorData = sensorService.getById(id);
		if (!sensorData.isPresent()) {
			return ResponseEntity.noContent().build();
		} else {
			SensorDto result = SensorDto.toDto(sensorData.get());
			return ResponseEntity.ok(result);
		}
	}

	@PostMapping("/sensors")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createSensor(@RequestBody SensorDto sensorDto) {
		log.info("Try add data");
		log.info("Add DTO " + sensorDto.toString());
		Long addedId = sensorService.add(sensorDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new MessageResponse(String.format(ADD_MSG_FORMAT, addedId)));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/sensors/{id}")
	public ResponseEntity<?> updateSensor(@PathVariable("id") Long id, @RequestBody SensorDto newSensor) {
		log.info("Try edit data {}", id);
		Long editedId = sensorService.edit(id, newSensor);
		return ResponseEntity.ok().body(new MessageResponse(String.format(EDIT_MSG_FORMAT, editedId)));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/sensors/{id}")
	public ResponseEntity<?> deleteSensor(@PathVariable("id") Long id) {
		log.info("Try delete data by id {}", id);
		try {
			sensorService.remove(id);
			return ResponseEntity.ok().body(new MessageResponse(String.format(DELETE_MSG_FORMAT, id)));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
