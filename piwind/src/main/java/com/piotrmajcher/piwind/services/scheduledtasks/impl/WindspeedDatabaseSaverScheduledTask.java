package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.repositories.WindSpeedRepository;
import com.piotrmajcher.piwind.services.scheduledtasks.DatabaseSaverScheduledTask;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;
import com.piotrmajcher.piwind.services.utils.WindSpeedReader;
import com.piotrmajcher.piwind.services.utils.WindSpeedReaderException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WindspeedDatabaseSaverScheduledTask implements DatabaseSaverScheduledTask{

    private static final Logger logger = Logger.getLogger(WindspeedDatabaseSaverScheduledTask.class);

    private static final String INFO_FETCHED_WINDSPEED_DATA = "Fetched windspeed data:";
    private static final String ERROR_SCHEDULED_TASK_FAILED = "Failed to execute wind speed scheduled task:";

    @Autowired
    private WindSpeedRepository windSpeedRepository;

    @Autowired
    private WindSpeedReader windSpeedReader;

    @Scheduled(fixedRate = 30000, initialDelay = 20000)
    @Override
    public void fetchDataAndSaveToDatabase() {
        try {
            saveWindSpeed();
        } catch (ScheduledTaskException e) {
            logger.error(ERROR_SCHEDULED_TASK_FAILED + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveWindSpeed() throws ScheduledTaskException {
        WindSpeed windSpeed;
        try {
            windSpeed = windSpeedReader.fetchWindSpeed();
        } catch (WindSpeedReaderException e) {
            throw new ScheduledTaskException(e.getMessage());
        }
        windSpeedRepository.save(windSpeed);
        logger.info(INFO_FETCHED_WINDSPEED_DATA +
                " measurement time: " + windSpeed.getMeasurementTimeSeconds() +
                " [s], wind speed: " + windSpeed.getWindSpeedMPS() + " [mps]");
    }
}
