package com.piotrmajcher.piwind.repositories;

import com.piotrmajcher.piwind.domain.InternalTemperature;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface InternalTemperatureRepository extends CrudRepository<InternalTemperature, Integer> {
	List<InternalTemperature> findByDateOrderByIdDesc(LocalDate date);
}