package com.piotrmajcher.piwind.web;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.services.TemperatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temperature")
@CrossOrigin
@Api(
		description = "\nEndpoint for getting the station's temperature measurements and statistics. " +
				"Internal temperature refers to internal Raspberry Pi's container temperature, " +
				"necessary for administration purposes to prevent the computer from overheating. " +
				"The external temperature refers to the outside temperature measured by the meteo station."
)
public class TemperatureController {
	
	private final TemperatureService temperatureService;

	@Autowired
	public TemperatureController(TemperatureService temperatureService) {
		this.temperatureService = temperatureService;
	}

	@GetMapping(path="/all-external")
	@CrossOrigin
	@ApiOperation( value = "Returns all external temperature measurements.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched all external temperature measurements.")
	})
	public ResponseEntity<List<ExternalTemperature>> getAllExternalTemperatureData() {
		return new ResponseEntity<>(temperatureService.getAllExternalTemperatureData(), HttpStatus.OK);
	}
	
	@GetMapping(path="/all-internal")
	@CrossOrigin
	@ApiOperation( value = "Returns all internal temperature measurements.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched all internal temperature measurements.")
	})
	public ResponseEntity<List<InternalTemperature>> getAllInternalTemperatureData() {
		return new ResponseEntity<>(temperatureService.getAllInternalTemperatureData(), HttpStatus.OK);
	}

	@GetMapping(path="/last-external")
	@CrossOrigin
	@ApiOperation( value = "Returns the latest external temperature measurement.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched last external temperature measurement.")
	})
	public @ResponseBody ExternalTemperature getLastExternalTemperatureMeasurement() throws Exception {
		return temperatureService.getLastExternalTemperatureMeasurement();
	}

	@GetMapping(path="/last-internal")
	@CrossOrigin
	@ApiOperation( value = "Returns the latest internal temperature measurement.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched last internal temperature measurement.")
	})
	public @ResponseBody InternalTemperature getLastInternalTemperatureMeasurement() throws Exception {
		return temperatureService.getLastInternalTemperatureMeasurement();
	}
}
