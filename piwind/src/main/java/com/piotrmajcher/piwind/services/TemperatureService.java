package com.piotrmajcher.piwind.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

public interface TemperatureService {
	
	public void fetchAndSaveInternalTemperature();
	
	public void fetchAndSaveExternalTemperature();
}
