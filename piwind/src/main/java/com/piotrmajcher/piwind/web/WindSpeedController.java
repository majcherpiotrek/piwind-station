package com.piotrmajcher.piwind.web;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.services.WindSpeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/windspeed")
@CrossOrigin
@Api(
        description = "\nEndpoint for getting the wind speed measurements and statistics. Data available in meters per second, " +
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
    public @ResponseBody Iterable<WindSpeed> getAllWindSpeedData() {
        return windSpeedService.getAllWindSpeedMeasurements();
    }
}
