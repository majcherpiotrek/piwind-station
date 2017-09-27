package com.piotrmajcher.piwind.repositories;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.domain.InternalTemperature;

import java.util.Date;
import java.util.List;

public interface InternalTemperatureRepository extends CrudRepository<InternalTemperature, Integer> {
	List<InternalTemperature> findByDateOrderByIdDesc(Date date);
}