package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "sensors")
public class Sensor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank
	@Column(name = "model_name", nullable = false)
	private String model;

	@Column(name = "range_from")
	private Integer rangeFrom;

	@Column(name = "range_to")
	private Integer rangeTo;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_id", referencedColumnName = "id")
	private Unit unit;

	@Column(name = "location")
	private String location;

	@Column(name = "description")
	private String description;

}
