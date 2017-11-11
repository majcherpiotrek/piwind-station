package com.piotrmajcher.piwind.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.piotrmajcher.piwind.dto.MeteoDataTO;
import com.piotrmajcher.piwind.services.MeteoDataService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
	
	@GetMapping(path = "/last/{minutes}/minutes")
    @CrossOrigin
    @ApiOperation( value = "Returns the meteo data from the last X minutes.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully fetched measurements from the specified period."),
            @ApiResponse(code = 400, message = "Incorrect minutes parameter")
    })
    public ResponseEntity<List<MeteoDataTO>> getAllWindSpeedMeasurementsFromLastXMinutes(
            @ApiParam(
                    name = "minutes",
                    value = "Specifies the period of time from which to get the meteo data measurements. " +
                            "The value should be in minutes as an unsigned integer.",
                    required = true)
            @PathVariable int minutes) {
        if (minutes <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
         return new ResponseEntity<>(meteoDataService.getMeteoDataFromLastXMinutes(minutes), HttpStatus.OK);
    }
}
