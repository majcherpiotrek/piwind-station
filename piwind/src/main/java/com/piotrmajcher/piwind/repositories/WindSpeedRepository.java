package com.piotrmajcher.piwind.repositories;


import com.piotrmajcher.piwind.domain.WindSpeed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WindSpeedRepository extends CrudRepository<WindSpeed, Integer> {

    List<WindSpeed> findByDateOrderByIdDesc(LocalDate date);

    @Query("SELECT wind FROM WindSpeed wind WHERE wind.dateTime >= :dateTime")
    List<WindSpeed> findAllMeasurementsFromSpecifiedDateTimeTillNow(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT wind FROM WindSpeed wind WHERE wind.dateTime = (SELECT MAX(wind2.dateTime) FROM WindSpeed wind2 WHERE wind2.date = :localDate)")
    WindSpeed findLastMeasurementFromDate(@Param("localDate") LocalDate localDate);

    List<WindSpeed> findAll();
}
