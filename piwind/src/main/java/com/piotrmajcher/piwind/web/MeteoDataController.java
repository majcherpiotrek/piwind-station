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

import com.piotrmajcher.piwind.services.MeteoDataService;
import com.piotrmajcher.piwind.services.InternalTemperatureService;
import com.piotrmajcher.piwind.services.WindSpeedService;
import com.piotrmajcher.piwind.tos.MeteoDataTO;
import com.piotrmajcher.piwind.tos.TemperatureTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/meteo")
@CrossOrigin
public class MeteoDataController {
	
	private final MeteoDataService meteoDataService;
	
	@Autowired
	public MeteoDataController(MeteoDataService meteoDataService) {
		this.meteoDataService = meteoDataService;
	}
	
	@GetMapping(path="/all")
	@CrossOrigin
	@ApiOperation( value = "Returns all meteo data.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched all meteo data.")
	})
	public ResponseEntity<List<MeteoDataTO>> getAllInternalTemperatureData() {
		
		return new ResponseEntity<>(meteoDataService.getAllMeasurements(), HttpStatus.OK);
	}
	
	@GetMapping(path="/last")
	@CrossOrigin
	@ApiOperation( value = "Returns the latest meteo data.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched last meteo data.")
	})
	public @ResponseBody MeteoDataTO getLastExternalTemperatureMeasurement() throws Exception {
		return meteoDataService.getLastMeasurement();
	}
}
