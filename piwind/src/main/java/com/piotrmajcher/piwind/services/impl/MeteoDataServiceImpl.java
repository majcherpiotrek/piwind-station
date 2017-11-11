package com.piotrmajcher.piwind.services.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piotrmajcher.piwind.domain.MeteoData;
import com.piotrmajcher.piwind.repositories.MeteoDataRepository;
import com.piotrmajcher.piwind.services.MeteoDataService;
import com.piotrmajcher.piwind.tos.MeteoDataTO;

@Service
public class MeteoDataServiceImpl implements MeteoDataService {
	
	private MeteoDataRepository meteoDataRepository;
	
	@Autowired
	public MeteoDataServiceImpl(MeteoDataRepository meteoDataRepository) {
		this.meteoDataRepository = meteoDataRepository;
	}
	
	@Override
	public List<MeteoDataTO> getAllMeasurements() {
		List<MeteoData> meteoDataList = meteoDataRepository.findAll();
		return createMeteoDataTOList(meteoDataList);
	}

	@Override
	public MeteoDataTO getLastMeasurement() {
		MeteoData meteoData = meteoDataRepository.findLastMeasurement();
		return convertToTO(meteoData);
	}

	private MeteoDataTO convertToTO(MeteoData meteoData) {
		MeteoDataTO meteoDataTO = new MeteoDataTO();
		meteoDataTO.setTemperature(meteoData.getTemperatureCelsius());
		meteoDataTO.setWindDirection(meteoData.getWindDirection());
		meteoDataTO.setWindSpeed(meteoData.getWindSpeedMPS());
		meteoDataTO.setDateTime(meteoData.getDateTime());
		return meteoDataTO;
	}
	@Override
	public List<MeteoDataTO> getMeteoDataFromLastXMinutes(int minutes) {
		LocalDateTime fromDate = LocalDateTime.now().minusMinutes(minutes);
		List<MeteoData> meteoDataList = meteoDataRepository.findAllMeasurementsFromSpecifiedDateTimeTillNow(fromDate);
		return createMeteoDataTOList(meteoDataList);
	}
	
	private List<MeteoDataTO> createMeteoDataTOList(List<MeteoData> meteoDataList) {
		List<MeteoDataTO> meteoDataTOList = new LinkedList<>();
		for (MeteoData meteoData : meteoDataList) {
			meteoDataTOList.add(convertToTO(meteoData));
		}
		return meteoDataTOList;
	}
}
