package com.example.payload;

import javax.validation.constraints.NotBlank;

import com.example.models.Sensor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorDto {

	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String model;
	
	@NotBlank
	private Long unitId;

	private String unit;

	@NotBlank
	private Long typeId;
	
	private String type;
	
	private Integer rangeFrom;
	
	private Integer rangeTo;

	private String range;

	private String location;

	private String description;

	public static SensorDto toDto(Sensor sensor) {
		return SensorDto.builder()
				.id(sensor.getId())
				.name(sensor.getName())
				.model(sensor.getModel())
				.type(sensor.getUnit().getSensorType().getName())
				.typeId(sensor.getUnit().getSensorType().getId())
				.unit(sensor.getUnit().getName())
				.unitId(sensor.getUnit().getId())
				.rangeFrom(sensor.getRangeFrom())
				.rangeTo(sensor.getRangeTo())
				.range(sensor.getRangeFrom() + "—" + sensor.getRangeTo())
				.location(sensor.getLocation())
				.description(sensor.getDescription()).build();
	}

}
