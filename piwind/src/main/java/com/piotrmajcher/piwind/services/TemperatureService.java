package com.piotrmajcher.piwind.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;

public interface TemperatureService {
	
	public Iterable<ExternalTemperature> getAllExternalTemperatureData();
	
	public Iterable<InternalTemperature> getAllInternalTemperatureData();
	
	public ExternalTemperature getLastExternalTemperatureMeasurement() throws Exception;
	
	public InternalTemperature getLastInternalTemperatureMeasurement() throws Exception;
}
