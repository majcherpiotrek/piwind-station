package com.piotrmajcher.piwind.web;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.services.WindSpeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/windspeed")
@CrossOrigin
public class WindSpeedController {

    @Autowired
    private WindSpeedService windSpeedService;

    @GetMapping(path = "/all-data")
    @CrossOrigin
    public @ResponseBody Iterable<WindSpeed> getAllWindSpeedData() {
        return windSpeedService.getAllWindSpeedMeasurements();
    }
}
