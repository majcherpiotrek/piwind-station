package com.piotrmajcher.piwind.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.piotrmajcher.piwind.domain.MeteoData;

public interface MeteoDataRepository extends CrudRepository<MeteoData, Long> {

	List<MeteoData> findAll();
	
	@Query("SELECT meteo FROM MeteoData meteo WHERE meteo.dateTime >= :dateTime ORDER BY meteo.id ASC")
	List<MeteoData> findAllMeasurementsFromSpecifiedDateTimeTillNow(@Param("dateTime") LocalDateTime dateTime);
	
	@Query("SELECT meteo FROM MeteoData meteo WHERE meteo.id = (SELECT MAX(meteo2.id) FROM MeteoData meteo2)")
	MeteoData findLastMeasurement();
}
