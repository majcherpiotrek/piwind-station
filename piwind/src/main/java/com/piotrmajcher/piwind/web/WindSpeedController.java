package com.piotrmajcher.piwind.web;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.services.WindSpeedService;
import com.piotrmajcher.piwind.tos.WindSpeedTO;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/windspeed")
@CrossOrigin
@Api(
        description = "Endpoint for getting the wind speed measurements and statistics. Data available in meters per second, " +
                "knots or kilometers per hour."
)
public class WindSpeedController {

    private final WindSpeedService windSpeedService;

    @Autowired
    public WindSpeedController(WindSpeedService windSpeedService) {
        this.windSpeedService = windSpeedService;
    }

    @GetMapping(path = "/all-data")
    @CrossOrigin
    @ApiOperation( value = "Returns all wind speed measurements.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully fetched all wind speed measurements.")
    })
    public @ResponseBody List<WindSpeedTO> getAllWindSpeedData() {
        return windSpeedService.getAllWindSpeedMeasurements();
    }

    @GetMapping(path = "/last-measurement")
    @CrossOrigin
    @ApiOperation( value = "Returns the latest wind speed measurement.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully fetched the latest wind speed measurement.")
    })
    public @ResponseBody WindSpeedTO getLastWindSpeedMeasurement() {
        return windSpeedService.getLatestWindSpeedMeasurement();
    }

    @GetMapping(path = "/last/{minutes}/minutes")
    @CrossOrigin
    @ApiOperation( value = "Returns the winds speed measurements from the last X minutes.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully fetched measurements form the specified period."),
            @ApiResponse(code = 400, message = "Incorrect minutes parameter")
    })
    public ResponseEntity<List<WindSpeedTO>> getAllWindSpeedMeasurementsFromLastXMinutes(
            @ApiParam(
                    name = "minutes",
                    value = "Specifies the period of time from which to get the wind speed measurements. " +
                            "The value should be in minutes as an unsigned integer.",
                    required = true)
            @PathVariable int minutes) {
        if (minutes <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
         return new ResponseEntity<>(windSpeedService.getWindSpeedMeasurementsFromLastXMinutes(minutes), HttpStatus.OK);
    }

    @GetMapping(path = "/last/{minutes}/minutes/average/knots")
    @CrossOrigin
    @ApiOperation( value = "Returns the avarage windspeed from all the measurements from the last X minutes in knots.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully calculated average wind speed."),
            @ApiResponse(code = 400, message = "Incorrect minutes parameter")
    })
    public ResponseEntity<Double> getAverageWindFromLastXMinutesKnots(
            @ApiParam(
                    name = "minutes",
                    value = "Specifies the period of time from which to count the average wind speed. " +
                            "The value should be in minutes as an unsigned integer.",
                    required = true)
            @PathVariable int minutes) {
        if (minutes <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(windSpeedService.getAverageWindSpeedFromLastXMinutesKnots(minutes), HttpStatus.OK);
    }

    @GetMapping(path = "/last/{minutes}/minutes/average/mps")
    @CrossOrigin
    @ApiOperation( value = "Returns the avarage windspeed from all the measurements from the last X minutes in meters per second.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully calculated average wind speed."),
            @ApiResponse(code = 400, message = "Incorrect minutes parameter")
    })
    public ResponseEntity<Double> getAverageWindFromLastXMinutesMps(
            @ApiParam(
                    name = "minutes",
                    value = "Specifies the period of time from which to count the average wind speed. " +
                            "The value should be in minutes as an unsigned integer.",
                    required = true)
            @PathVariable int minutes) {
        if (minutes <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(windSpeedService.getAverageWindSpeedFromLastXMinutesMps(minutes), HttpStatus.OK);
    }

    @GetMapping(path = "/last/{minutes}/minutes/average/kmh")
    @CrossOrigin
    @ApiOperation( value = "Returns the avarage windspeed from all the measurements from the last X minutes in kilmeters per hour.")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully calculated average wind speed."),
            @ApiResponse(code = 400, message = "Incorrect minutes parameter")
    })
    public ResponseEntity<Double> getAverageWindFromLastXMinutesKmh(
            @ApiParam(
                    name = "minutes",
                    value = "Specifies the period of time from which to count the average windspeed. " +
                            "The value should be in minutes as an unsigned integer.",
                    required = true)
            @PathVariable int minutes) {
        if (minutes <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(windSpeedService.getAverageWindSpeedFromLastXMinutesKmh(minutes), HttpStatus.OK);
    }
}
