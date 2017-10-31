package com.piotrmajcher.piwind.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExternalTemperatureRepository extends CrudRepository<ExternalTemperature, Integer> {

    List<ExternalTemperature> findAll();

    @Query("SELECT temp FROM ExternalTemperature temp WHERE temp.id = (SELECT MAX(temp2.id) FROM ExternalTemperature temp2)")
    ExternalTemperature findLastMeasurement();
}
