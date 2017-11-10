package com.piotrmajcher.piwind.services;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.tos.TemperatureTO;

import java.util.List;

public interface InternalTemperatureService {
		
	List<TemperatureTO> getAllInternalTemperatureData();
	
	TemperatureTO getLastInternalTemperatureMeasurement();
}
