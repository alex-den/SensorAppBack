package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.models.Sensor;
import com.example.models.SensorType;
import com.example.models.Unit;
import com.example.repository.SensorRepository;
import com.example.repository.SensorTypeRepository;
import com.example.repository.UnitRepository;

@DataJpaTest
public class JPAUnitTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	SensorRepository sensorRepository;

	@Autowired
	UnitRepository unitRepository;

	@Autowired
	SensorTypeRepository sensorTypeRepository;

	@Test
	public void should_find_no_sensors_if_repository_is_empty() {
		Iterable sensors = sensorRepository.findAll();
		assertThat(sensors).isEmpty();
	}

	@Test
	public void should_store_a_sensor() {

		SensorType st1 = new SensorType();
		st1.setName("Voltage");
		entityManager.persist(st1);

		Unit u1 = new Unit();
		u1.setName("voltage");
		u1.setSensorType(sensorTypeRepository.findByName("Voltage").get());
		entityManager.persist(u1);

		Sensor s1 = new Sensor();
		s1.setName("Sensor1");
		s1.setModel("TER-21");
		s1.setUnit(unitRepository.findByName("voltage").get());

		Sensor insertedSensor = sensorRepository.save(s1);
		assertThat(entityManager.find(Sensor.class, insertedSensor.getId())).isEqualTo(s1);
	}

	@Test
	public void should_find_all_sensors() {

		SensorType st1 = new SensorType();
		st1.setName("Voltage");
		entityManager.persist(st1);

		Unit u1 = new Unit();
		u1.setName("voltage");
		u1.setSensorType(sensorTypeRepository.findByName("Voltage").get());
		entityManager.persist(u1);

		Sensor s1 = new Sensor();
		s1.setName("Sensor1");
		s1.setModel("TER-21");
		s1.setUnit(unitRepository.findByName("voltage").get());
		entityManager.persist(s1);

		Sensor s2 = new Sensor();
		s2.setName("Sensor2");
		s2.setModel("PH-50-36");
		s2.setUnit(unitRepository.findByName("voltage").get());
		entityManager.persist(s2);

		Iterable sensors = sensorRepository.findAll();
		assertThat(sensors).hasSize(2).contains(s1, s2);
	}

	@Test
	public void should_find_all_sensors_with_search_text() {

		SensorType st1 = new SensorType();
		st1.setName("Preasure");
		entityManager.persist(st1);

		Unit u1 = new Unit();
		u1.setName("bar");
		u1.setSensorType(sensorTypeRepository.findByName("Preasure").get());
		entityManager.persist(u1);

		Sensor s1 = new Sensor();
		s1.setName("Sensor1");
		s1.setModel("SV-500");
		s1.setUnit(unitRepository.findByName("bar").get());
		entityManager.persist(s1);

		Sensor s2 = new Sensor();
		s2.setName("Sensor2");
		s2.setModel("SVT-500");
		s2.setUnit(unitRepository.findByName("bar").get());
		entityManager.persist(s2);

		Iterable sensors = sensorRepository.findAllByText("SV");
		assertThat(sensors).hasSize(2).contains(s1, s2);
		sensors = sensorRepository.findAllByText("SVT");
		assertThat(sensors).hasSize(1).contains(s2);

	}

	@Test
	public void should_find_all_unit_for_sensor_type() {

		SensorType st1 = new SensorType();
		st1.setName("Temperature");
		entityManager.persist(st1);

		Long serchId = sensorTypeRepository.findByName("Temperature").get().getId();
		Unit u1 = new Unit();
		u1.setName("°C");
		u1.setSensorType(sensorTypeRepository.findByName("Temperature").get());
		entityManager.persist(u1);

		Unit u2 = new Unit();
		u2.setName("°F");
		u2.setSensorType(sensorTypeRepository.findByName("Temperature").get());
		entityManager.persist(u2);

		Iterable units = unitRepository.findAllBySensorTypeId(serchId);
		assertThat(units).hasSize(2).contains(u1, u2);

	}
}
