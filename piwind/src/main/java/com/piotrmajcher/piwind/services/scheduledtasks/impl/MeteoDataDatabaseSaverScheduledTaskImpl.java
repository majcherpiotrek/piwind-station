package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.domain.MeteoData;
import com.piotrmajcher.piwind.repositories.MeteoDataRepository;
import com.piotrmajcher.piwind.sensordata.WindSpeed;
import com.piotrmajcher.piwind.services.scheduledtasks.DatabaseSaverScheduledTask;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;
import com.piotrmajcher.piwind.services.utils.TemperatureReader;
import com.piotrmajcher.piwind.services.utils.WindSpeedReader;
import com.piotrmajcher.piwind.services.utils.exceptions.TemperatureReaderException;
import com.piotrmajcher.piwind.services.utils.exceptions.WindSpeedReaderException;

@Component
public class MeteoDataDatabaseSaverScheduledTaskImpl implements DatabaseSaverScheduledTask {
	
	private static final Logger logger = Logger.getLogger(MeteoDataDatabaseSaverScheduledTaskImpl.class);

	private static final String FETCH_TEMP_FAILED = "Fetching the external temperature failed";
	private static final String FETCH_WIND_FAILED = "Fetching the wind speed failed";
	private static final String SAVED_METEO_DATA = "Saved new meteo data: ";
    
    private MeteoDataRepository meteoDataRepository;
    private TemperatureReader temperatureReader;
    private WindSpeedReader windSpeedReader;
    
    
    @Autowired
    public MeteoDataDatabaseSaverScheduledTaskImpl(
    		MeteoDataRepository meteoDataRepository, 
    		TemperatureReader temperatureReader, 
    		WindSpeedReader windSpeedReader) {
		this.meteoDataRepository = meteoDataRepository;
		this.temperatureReader = temperatureReader;
		this.windSpeedReader = windSpeedReader;
		
	}
    
    @Scheduled(fixedRate = 4000)
	@Override
	public void fetchDataAndSaveToDatabase() {
    	
    	try {
			fetchAndSaveMeteoData();
		} catch (ScheduledTaskException e) {
			logger.error(e.getMessage());
		}
	}

	private void fetchAndSaveMeteoData() throws ScheduledTaskException {
		Double temperature = null;
    	WindSpeed windSpeed = null;
    	
    	try {
			temperature = temperatureReader.fetchExternalTemperature();
		} catch (TemperatureReaderException e) {
			throw new ScheduledTaskException(FETCH_TEMP_FAILED);
		}
    	
    	try {
			windSpeed = windSpeedReader.fetchWindSpeed();
		} catch (WindSpeedReaderException e) {
			throw new ScheduledTaskException(FETCH_WIND_FAILED);
		}
    	
    	MeteoData meteoData = new MeteoData();
		meteoData.setTemperatureCelsius(temperature);
		meteoData.setWindSpeedMPS(windSpeed.getWindSpeedMPS());
		meteoData.setWindDirection(windSpeed.getWindDirection());
		meteoData.setWindSpeedMeasurementTimeSeconds(windSpeed.getMeasurementTimeSeconds());
		
		meteoDataRepository.save(meteoData);
		logger.debug(SAVED_METEO_DATA + meteoData.toString());
	}
}
