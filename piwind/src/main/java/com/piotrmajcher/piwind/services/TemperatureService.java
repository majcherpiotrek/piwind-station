package com.piotrmajcher.piwind.services;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;

import java.util.List;

public interface TemperatureService {
	
	List<ExternalTemperature> getAllExternalTemperatureData();
	
	List<InternalTemperature> getAllInternalTemperatureData();
	
	ExternalTemperature getLastExternalTemperatureMeasurement();
	
	InternalTemperature getLastInternalTemperatureMeasurement();
}
