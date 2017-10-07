package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.repositories.WindSpeedRepository;
import com.piotrmajcher.piwind.services.scheduledtasks.DatabaseSaverScheduledTask;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;
import com.piotrmajcher.piwind.services.utils.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.CommandExecutor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WindspeedDatabaseSaverScheduledTask implements DatabaseSaverScheduledTask{

    private static final Logger logger = Logger.getLogger(WindspeedDatabaseSaverScheduledTask.class);

    private static final String FETCH_WINDSPEED_COMMAND= "python ./scripts/windmeter.py";
    private static final String FETCHED_NULL_VALUE = "Received null value while trying to fetch the windspeed data";
    private static final String INFO_FETCHED_WINDSPEED_DATA = "Fetched windspeed data:";
    private static final String ERROR_COMMAND_FAILED = "Failed to execute fetch wind speed command:";
    private static final String ERROR_SCHEDULED_TASK_FAILED = "Failed to execute wind speed scheduled task:";

    @Autowired
    CommandExecutor commandExecutor;

    @Autowired
    WindSpeedRepository windSpeedRepository;

    @Scheduled(fixedRate = 30000, initialDelay = 15000)
    @Override
    public void fetchDataAndSaveToDatabase() {
        try {
            saveWindSpeed();
        } catch (CommandExecutionException e) {
            logger.error(ERROR_COMMAND_FAILED + " " + e.getMessage());
            logger.error(e.getStackTrace());
        } catch (ScheduledTaskException e) {
            logger.error(ERROR_SCHEDULED_TASK_FAILED + " " + e.getMessage());
            logger.error(e.getStackTrace());
        }
    }

    private void saveWindSpeed() throws CommandExecutionException, ScheduledTaskException {
        WindSpeed windSpeed = fetchWindSpeed();
        windSpeedRepository.save(windSpeed);
        logger.info(INFO_FETCHED_WINDSPEED_DATA +
                " measurement time: " + windSpeed.getMeasurementTimeSeconds() +
                " [s], wind speed: " + windSpeed.getWindSpeedMPS() + " [mps]");
    }
    private WindSpeed fetchWindSpeed() throws CommandExecutionException, ScheduledTaskException {
        WindSpeed windSpeed = new WindSpeed();
        List<String> commandResultList = commandExecutor.executeCommand(FETCH_WINDSPEED_COMMAND);
        if (commandResultList == null || commandResultList.size() != 2) {
            throw new ScheduledTaskException(FETCHED_NULL_VALUE);
        } else {
            Integer measurementTime = Integer.valueOf(commandResultList.get(0));
            Double windSpeedMPS = Double.valueOf(commandResultList.get(1));

            windSpeed.setMeasurementTimeSeconds(measurementTime);
            windSpeed.setWindSpeedMPS(windSpeedMPS);
        }
        return windSpeed;
    }
}
