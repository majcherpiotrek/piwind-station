package com.piotrmajcher.piwind.repositories;

import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.domain.ExternalTemperature;

import java.time.LocalDate;
import java.util.List;

public interface ExternalTemperatureRepository extends CrudRepository<ExternalTemperature, Integer> {
    List<ExternalTemperature> findByDateOrderByIdDesc(LocalDate date);
}
