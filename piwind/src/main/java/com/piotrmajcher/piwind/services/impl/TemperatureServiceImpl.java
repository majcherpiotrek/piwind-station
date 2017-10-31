package com.piotrmajcher.piwind.services.impl;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.TemperatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TemperatureServiceImpl implements TemperatureService {
	
	private static final Logger logger = Logger.getLogger(TemperatureServiceImpl.class);

	@Autowired
	private InternalTemperatureRepository internalTemperatureRepository;
	
	@Autowired
	private ExternalTemperatureRepository externalTemperatureRepository;

	@Override
	public Iterable<ExternalTemperature> getAllExternalTemperatureData() {
		return externalTemperatureRepository.findAll();
	}

	@Override
	public Iterable<InternalTemperature> getAllInternalTemperatureData() {
		return internalTemperatureRepository.findAll();
	}

	@Override
	public ExternalTemperature getLastExternalTemperatureMeasurement() {
		LocalDate localDate = LocalDate.now();
		List<ExternalTemperature> todayMeasurementsSortedDescending = externalTemperatureRepository.findByDateOrderByIdDesc(localDate);
		return todayMeasurementsSortedDescending.get(0);
	}

	@Override
	public InternalTemperature getLastInternalTemperatureMeasurement() {
		LocalDate localDate = LocalDate.now();
		List<InternalTemperature> todayMeasurementsSortedDescending = internalTemperatureRepository.findByDateOrderByIdDesc(localDate);
		return todayMeasurementsSortedDescending.get(0);
	}
}
