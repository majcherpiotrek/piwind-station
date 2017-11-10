package com.piotrmajcher.piwind.services.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.WindSpeedRepository;
import com.piotrmajcher.piwind.services.MeteoDataService;
import com.piotrmajcher.piwind.tos.MeteoDataTO;

@Service
public class MeteoDataServiceImpl implements MeteoDataService {
	
	private ExternalTemperatureRepository temperatureRepository;
	private WindSpeedRepository windSpeedRepository;
	
	@Autowired
	public MeteoDataServiceImpl(ExternalTemperatureRepository temperatureRepository, WindSpeedRepository windSpeedRepository) {
		this.temperatureRepository = temperatureRepository;
		this.windSpeedRepository = windSpeedRepository;
	}
	@Override
	public List<MeteoDataTO> getAllMeasurements() {
		List<ExternalTemperature> temperatureList = temperatureRepository.findAll();
		List<WindSpeed> windSpeedList = windSpeedRepository.findAll();
		 
		return createMeteoDataTOList(temperatureList, windSpeedList);
	}

	@Override
	public MeteoDataTO getLastMeasurement() {
		ExternalTemperature temp = temperatureRepository.findLastMeasurement();
		WindSpeed wind = windSpeedRepository.findLastMeasurement();
		
		MeteoDataTO meteoDataTO = new MeteoDataTO();
		meteoDataTO.setTemperature(temp == null ? null : temp.getTemperatureCelsius());
		meteoDataTO.setWindSpeed(wind == null ? null : wind.getWindSpeedMPS());
		meteoDataTO.setDateTime(LocalDateTime.now());
		return meteoDataTO;
	}
	@Override
	public List<MeteoDataTO> getMeteoDataFromLastXMinutes(int minutes) {
		LocalDateTime fromDate = LocalDateTime.now().minusMinutes(minutes);
		List<ExternalTemperature> temperatureList = temperatureRepository.findAllMeasurementsFromSpecifiedDateTimeTillNow(fromDate);
		List<WindSpeed> windSpeedList = windSpeedRepository.findAllMeasurementsFromSpecifiedDateTimeTillNow(fromDate);
		return createMeteoDataTOList(temperatureList, windSpeedList);
	}
	
	private List<MeteoDataTO> createMeteoDataTOList(List<ExternalTemperature> temperatureList,
			List<WindSpeed> windSpeedList) {
		List<MeteoDataTO> meteoDataTOList = new LinkedList<>();
		int size = Math.max(temperatureList.size(), windSpeedList.size());
		for (int i=0; i<size; i++) {
			MeteoDataTO meteoDataTO = new MeteoDataTO();
			meteoDataTO.setTemperature( i < temperatureList.size() ? temperatureList.get(i).getTemperatureCelsius() : null);
			meteoDataTO.setWindSpeed(i < windSpeedList.size() ? windSpeedList.get(i).getWindSpeedMPS() : null);
			meteoDataTO.setDateTime(temperatureList.size() > windSpeedList.size() ? temperatureList.get(i).getDateTime() : windSpeedList.get(i).getDateTime());
			
			meteoDataTOList.add(meteoDataTO);
		}
		return meteoDataTOList;
	}
}
