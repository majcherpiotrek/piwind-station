package com.piotrmajcher.piwind.services.impl;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.repositories.WindSpeedRepository;
import com.piotrmajcher.piwind.services.WindSpeedService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class WindSpeedServiceImpl implements WindSpeedService{

    private static final Logger logger = Logger.getLogger(WindSpeedServiceImpl.class);

    private static final String FETCH_WINDSPEED_SCRIPT_PATH = "./scripts/windmeter.py";
    private static final String ERROR_FETCHING_WINDSPEED = "Error while fetching windspeed data";
    private static final String ERROR_WHILE_EXECUTING_SCRIPT = "Error occured while executing following script: ";
    private static final String INFO_FETCHED_WINDSPEED_DATA = "Fetched windspeed data";

    @Autowired
    private WindSpeedRepository windSpeedRepository;

    @Scheduled(fixedRate = 30000, initialDelay = 15000)
    private void fetchAndSaveWindSpeedData() {
        try {
            WindSpeed windSpeed = executeFetchWindSpeedDataPythonScript();

            Assert.notNull(windSpeed, ERROR_FETCHING_WINDSPEED);

            logger.info(INFO_FETCHED_WINDSPEED_DATA + " " + windSpeed.getWindSpeedMPS() + "[mps]");
            windSpeedRepository.save(windSpeed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WindSpeed executeFetchWindSpeedDataPythonScript() throws IOException {
        final String command = "python" + " " + FETCH_WINDSPEED_SCRIPT_PATH;

        Process process = Runtime.getRuntime().exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String error = stdError.readLine();
        Assert.isNull(error, error);

        String line;
        Integer measurementTime;
        Double windSpeedMPS;

        line = stdInput.readLine();
        Assert.notNull(line, ERROR_FETCHING_WINDSPEED);
        measurementTime = Integer.valueOf(line);

        line = stdInput.readLine();
        Assert.notNull(line, ERROR_FETCHING_WINDSPEED);
        windSpeedMPS = Double.valueOf(line);

        WindSpeed windSpeed = new WindSpeed();
        windSpeed.setMeasurementTimeSeconds(measurementTime);
        windSpeed.setWindSpeedMPS(windSpeedMPS);

        return windSpeed;
    }
    @Override
    public WindSpeed getLatestWindSpeedMeasurement() throws Exception {
        return null;
    }

    @Override
    public Iterable<WindSpeed> getAllWindSpeedMeasurements() {
        return windSpeedRepository.findAll();
    }
}
