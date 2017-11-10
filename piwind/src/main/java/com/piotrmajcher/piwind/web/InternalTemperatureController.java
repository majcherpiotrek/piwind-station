package com.piotrmajcher.piwind.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.piotrmajcher.piwind.services.InternalTemperatureService;
import com.piotrmajcher.piwind.tos.TemperatureTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/internal-temperature")
@CrossOrigin
public class InternalTemperatureController {
	
	private final InternalTemperatureService temperatureService;

	@Autowired
	public InternalTemperatureController(InternalTemperatureService temperatureService) {
		this.temperatureService = temperatureService;
	}
	
	@GetMapping(path="/all-internal")
	@CrossOrigin
	@ApiOperation( value = "Returns all internal temperature measurements.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched all internal temperature measurements.")
	})
	public ResponseEntity<List<TemperatureTO>> getAllInternalTemperatureData() {
		return new ResponseEntity<>(temperatureService.getAllInternalTemperatureData(), HttpStatus.OK);
	}

	@GetMapping(path="/last-internal")
	@CrossOrigin
	@ApiOperation( value = "Returns the latest internal temperature measurement.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched last internal temperature measurement.")
	})
	public @ResponseBody TemperatureTO getLastInternalTemperatureMeasurement() throws Exception {
		return temperatureService.getLastInternalTemperatureMeasurement();
	}
}
