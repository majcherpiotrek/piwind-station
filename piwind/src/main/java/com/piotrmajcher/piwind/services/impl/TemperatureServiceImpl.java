package com.piotrmajcher.piwind.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.TemperatureService;

@Component
public class TemperatureServiceImpl implements TemperatureService {
	
	private static final Logger logger = Logger.getLogger(TemperatureServiceImpl.class);
	
	private static final String FETCH_INTERNAL_TEMP_SCRIPT_PATH = "./scripts/read_internal_temperature.py";
	private static final String FETCH_EXTERNAL_TEMP_SCRIPT_PATH = "./scripts/read_external_temperature.py";
	private static final String ERROR_FETCHING_INTERNAL_TEMPERATURE = "Error while fetching internal temperature data";
	private static final String ERROR_FETCHING_EXTERNAL_TEMPERATURE = "Error while fetching external temperature data";
	private static final String ERROR_WHILE_EXECUTING_SCRIPT = "Error occured while executing following script: ";
	private static final String INFO_FETCHED_INTERNAL_TEMPERATURE = "Fetched internal temperature data";
	private static final String INFO_FETCHED_EXTERNAL_TEMPERATURE = "Fetched external temperature data";
	
	@Autowired
	private InternalTemperatureRepository internalTemperatureRepository;
	
	@Autowired
	private ExternalTemperatureRepository externalTemperatureRepository;
	
	@Transactional
	@Scheduled(fixedRate = 10000)
	private void fetchAndSaveTemperatureSensorsData() {
		fetchAndSaveExternalTemperature();
		fetchAndSaveInternalTemperature();
	}
	
	private void fetchAndSaveInternalTemperature() {
		try {
			Double temperatureDouble = executeFetchTemperaturePythonScript(FETCH_INTERNAL_TEMP_SCRIPT_PATH);
			
			Assert.notNull(temperatureDouble, ERROR_FETCHING_INTERNAL_TEMPERATURE);
			
			InternalTemperature internalTemperature = new InternalTemperature();
			internalTemperature.setTemperatureCelsius(temperatureDouble);
			logger.info(INFO_FETCHED_INTERNAL_TEMPERATURE);
			internalTemperatureRepository.save(internalTemperature);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	private void fetchAndSaveExternalTemperature()  {
		try {
			Double temperatureDouble = executeFetchTemperaturePythonScript(FETCH_EXTERNAL_TEMP_SCRIPT_PATH);
			
			Assert.notNull(temperatureDouble, ERROR_FETCHING_EXTERNAL_TEMPERATURE);
			
			ExternalTemperature externalTemperature = new ExternalTemperature();
			logger.info(INFO_FETCHED_EXTERNAL_TEMPERATURE);
			externalTemperatureRepository.save(externalTemperature);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private Double executeFetchTemperaturePythonScript(String scriptPath) throws IOException {
		
		final String command = "python" + " " + scriptPath;
		
		Process process = Runtime.getRuntime().exec(command);
			 
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		String error = stdError.readLine();
		Assert.isNull(error, error);
		
		String tempString = stdInput.readLine();
		Assert.notNull(tempString, ERROR_WHILE_EXECUTING_SCRIPT + scriptPath);
		
		return Double.valueOf(tempString);
	}

	@Override
	public Iterable<ExternalTemperature> getAllExternalTemperatureData() {
		return externalTemperatureRepository.findAll();
	}

	@Override
	public Iterable<InternalTemperature> getAllInternalTemperatureData() {
		return internalTemperatureRepository.findAll();
	}

	@Override
	public ExternalTemperature getLastExternalTemperatureMeasurement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InternalTemperature getLastInternalTemperatureMeasurement() {
		// TODO Auto-generated method stub
		return null;
	}

}
