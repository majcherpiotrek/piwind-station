package com.piotrmajcher.piwind.repositories;


import com.piotrmajcher.piwind.domain.WindSpeed;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface WindSpeedRepository extends CrudRepository<WindSpeed, Integer> {
    List<WindSpeed> findByDateOrderByIdDesc(Date date);
}
