package com.piotrmajcher.piwind.repositories;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.domain.ExternalTemperature;

public interface ExternalTemperatureRepository extends CrudRepository<ExternalTemperature, Integer>{

}
