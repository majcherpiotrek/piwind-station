package com.piotrmajcher.piwind.services.impl;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.TemperatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	public ExternalTemperature getLastExternalTemperatureMeasurement() throws Exception {
		//TODO what if findByDate returns null/empty list
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
		List<ExternalTemperature> todayMeasurementsSortedDescending = externalTemperatureRepository.findByDateOrderByIdDesc(dateWithoutTime);
		return todayMeasurementsSortedDescending.get(0);
	}

	@Override
	public InternalTemperature getLastInternalTemperatureMeasurement() throws Exception {
		//TODO what if findByDate returns null/empty list
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
		List<InternalTemperature> todayMeasurementsSortedDescending = internalTemperatureRepository.findByDateOrderByIdDesc(dateWithoutTime);
		return todayMeasurementsSortedDescending.get(0);
	}
}
