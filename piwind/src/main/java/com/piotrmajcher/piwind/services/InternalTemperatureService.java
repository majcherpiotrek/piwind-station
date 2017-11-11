package com.piotrmajcher.piwind.services;

import java.util.List;

import com.piotrmajcher.piwind.dto.TemperatureTO;

public interface InternalTemperatureService {
		
	List<TemperatureTO> getAllInternalTemperatureData();
	
	TemperatureTO getLastInternalTemperatureMeasurement();
}
