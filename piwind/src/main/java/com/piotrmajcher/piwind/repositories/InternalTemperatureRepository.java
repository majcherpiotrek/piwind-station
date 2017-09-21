package com.piotrmajcher.piwind.repositories;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.domain.InternalTemperature;

public interface InternalTemperatureRepository extends CrudRepository<InternalTemperature, Integer> {
	
}