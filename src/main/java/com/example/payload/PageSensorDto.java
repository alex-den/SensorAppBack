package com.example.payload;

import java.util.List;

import lombok.Data;

@Data
public class PageSensorDto {
	
	private final List<SensorDto> sensors;
	private final Integer pageNumber;
	private final Long totalItems;
	private final Integer totalPage;

}
