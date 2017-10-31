package com.piotrmajcher.piwind.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.TemperatureService;

@RestController
@RequestMapping("/temperature")
@CrossOrigin
public class TemperatureController {
	
	@Autowired
	private TemperatureService temperatureService;
	
	@GetMapping(path="/all-external")
	@CrossOrigin
	public ResponseEntity<Iterable<ExternalTemperature>> getAllExternalTemperatureData() {
		return new ResponseEntity<>(temperatureService.getAllExternalTemperatureData(), HttpStatus.OK);
	}
	
	@GetMapping(path="/all-internal")
	@CrossOrigin
	public ResponseEntity<Iterable<InternalTemperature>> getAllInternalTemperatureData() {
		return new ResponseEntity<>(temperatureService.getAllInternalTemperatureData(), HttpStatus.OK);
	}

	@GetMapping(path="/last-external")
	@CrossOrigin
	public @ResponseBody ExternalTemperature getLastExternalTemperatureMeasurement() throws Exception {
		return temperatureService.getLastExternalTemperatureMeasurement();
	}

	@GetMapping(path="/last-internal")
	@CrossOrigin
	public @ResponseBody InternalTemperature getLastInternalTemperatureMeasurement() throws Exception {
		return temperatureService.getLastInternalTemperatureMeasurement();
	}
}
