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

	private final InternalTemperatureRepository internalTemperatureRepository;
	
	private final ExternalTemperatureRepository externalTemperatureRepository;

	@Autowired
	public TemperatureServiceImpl(InternalTemperatureRepository internalTemperatureRepository, ExternalTemperatureRepository externalTemperatureRepository) {
		this.internalTemperatureRepository = internalTemperatureRepository;
		this.externalTemperatureRepository = externalTemperatureRepository;
	}

	@Override
	public List<ExternalTemperature> getAllExternalTemperatureData() {
		return externalTemperatureRepository.findAll();
	}

	@Override
	public List<InternalTemperature> getAllInternalTemperatureData() {
		return internalTemperatureRepository.findAll();
	}

	@Override
	public ExternalTemperature getLastExternalTemperatureMeasurement() {
		return externalTemperatureRepository.findLastMeasurement();
	}

	@Override
	public InternalTemperature getLastInternalTemperatureMeasurement() {
		return internalTemperatureRepository.findLastMeasurement();
	}
}
