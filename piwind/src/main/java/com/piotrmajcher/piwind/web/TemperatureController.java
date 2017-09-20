package com.piotrmajcher.piwind.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.TemperatureService;

@Controller
@RequestMapping(path="/temperature")
@CrossOrigin
public class TemperatureController {
	
	@Autowired
	private InternalTemperatureRepository internalTemperatureRepository;
	
	@Autowired
	private ExternalTemperatureRepository externalTemperatureRepository;
	
	@GetMapping(path="/all-external")
	@CrossOrigin
	public @ResponseBody Iterable<ExternalTemperature> getAllExternalTemperatureData() {
		return externalTemperatureRepository.findAll();
	}
	
	@GetMapping(path="/all-internal")
	@CrossOrigin
	public @ResponseBody Iterable<InternalTemperature> getAllInternalTemperatureData() {
		return internalTemperatureRepository.findAll();
	}
}
