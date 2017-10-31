package com.piotrmajcher.piwind.repositories;

import com.piotrmajcher.piwind.domain.InternalTemperature;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InternalTemperatureRepository extends CrudRepository<InternalTemperature, Integer> {

	List<InternalTemperature> findAll();

	@Query("SELECT temp FROM InternalTemperature temp WHERE temp.id = (SELECT MAX(temp2.id) FROM InternalTemperature temp2)")
	InternalTemperature findLastMeasurement();
}