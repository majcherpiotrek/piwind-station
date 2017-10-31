package com.piotrmajcher.piwind.repositories;


import com.piotrmajcher.piwind.domain.WindSpeed;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WindSpeedRepository extends CrudRepository<WindSpeed, Integer> {
    List<WindSpeed> findByDateOrderByIdDesc(LocalDate date);
}
