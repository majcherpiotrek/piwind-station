package com.piotrmajcher.piwind.services.impl;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.repositories.WindSpeedRepository;
import com.piotrmajcher.piwind.services.WindSpeedService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WindSpeedServiceImpl implements WindSpeedService{

    private static final Logger logger = Logger.getLogger(WindSpeedServiceImpl.class);

    @Autowired
    private WindSpeedRepository windSpeedRepository;

    @Override
    public WindSpeed getLatestWindSpeedMeasurement() throws Exception {
        return null;
    }

    @Override
    public Iterable<WindSpeed> getAllWindSpeedMeasurements() {
        return windSpeedRepository.findAll();
    }
}
