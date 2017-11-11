package com.piotrmajcher.piwind.services;

import java.util.List;

import com.piotrmajcher.piwind.dto.MeteoDataTO;

public interface MeteoDataService {

	List<MeteoDataTO> getAllMeasurements();
	
	MeteoDataTO getLastMeasurement();
	
	List<MeteoDataTO> getMeteoDataFromLastXMinutes(int minutes);
}
