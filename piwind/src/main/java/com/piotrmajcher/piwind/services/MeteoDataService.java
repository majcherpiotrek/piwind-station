package com.piotrmajcher.piwind.services;

import java.util.List;

import com.piotrmajcher.piwind.tos.MeteoDataTO;
import com.piotrmajcher.piwind.tos.WindSpeedTO;

public interface MeteoDataService {

	List<MeteoDataTO> getAllMeasurements();
	MeteoDataTO getLastMeasurement();
	List<MeteoDataTO> getMeteoDataFromLastXMinutes(int minutes);
}
