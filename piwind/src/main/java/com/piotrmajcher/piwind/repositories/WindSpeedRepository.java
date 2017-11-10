package com.piotrmajcher.piwind.repositories;


import com.piotrmajcher.piwind.domain.WindSpeed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WindSpeedRepository extends CrudRepository<WindSpeed, Integer> {

    @Query("SELECT wind FROM WindSpeed wind WHERE wind.dateTime >= :dateTime")
    List<WindSpeed> findAllMeasurementsFromSpecifiedDateTimeTillNow(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT wind FROM WindSpeed wind WHERE wind.id = (SELECT MAX(wind2.id) FROM WindSpeed wind2)")
    WindSpeed findLastMeasurement();

    List<WindSpeed> findAll();
}
