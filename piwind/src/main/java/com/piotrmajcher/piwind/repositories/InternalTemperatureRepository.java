package com.piotrmajcher.piwind.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.domain.InternalTemperature;

public interface InternalTemperatureRepository extends CrudRepository<InternalTemperature, Integer> {

	List<InternalTemperature> findAll();

	@Query("SELECT temp FROM InternalTemperature temp WHERE temp.id = (SELECT MAX(temp2.id) FROM InternalTemperature temp2)")
	InternalTemperature findLastMeasurement();
}