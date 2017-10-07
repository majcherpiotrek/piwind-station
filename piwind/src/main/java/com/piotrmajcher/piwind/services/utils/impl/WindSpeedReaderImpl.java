package com.piotrmajcher.piwind.services.utils.impl;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.services.utils.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.CommandExecutor;
import com.piotrmajcher.piwind.services.utils.WindSpeedReader;
import com.piotrmajcher.piwind.services.utils.WindSpeedReaderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WindSpeedReaderImpl implements WindSpeedReader{

    private static final String FETCH_WINDSPEED_COMMAND= "python ./scripts/windmeter.py";
    private static final String FETCHED_NULL_VALUE = "Received null value while trying to fetch the windspeed data";

    @Autowired
    private CommandExecutor commandExecutor;

    @Override
    public WindSpeed fetchWindSpeed() throws WindSpeedReaderException {
        WindSpeed windSpeed = new WindSpeed();
        List<String> commandResultList;
        try {
            commandResultList = commandExecutor.executeCommand(FETCH_WINDSPEED_COMMAND);

            if (commandResultList == null
                    || commandResultList.size() != 2
                    || commandResultList.get(0) == null
                    || commandResultList.get(1) == null) {
                throw new WindSpeedReaderException(FETCHED_NULL_VALUE);
            } else {
                Integer measurementTime = Integer.valueOf(commandResultList.get(0));
                Double windSpeedMPS = Double.valueOf(commandResultList.get(1));

                windSpeed.setMeasurementTimeSeconds(measurementTime);
                windSpeed.setWindSpeedMPS(windSpeedMPS);
            }
        } catch (CommandExecutionException | NumberFormatException e) {
            throw new WindSpeedReaderException(e.getMessage());
        }
        return windSpeed;
    }
}
