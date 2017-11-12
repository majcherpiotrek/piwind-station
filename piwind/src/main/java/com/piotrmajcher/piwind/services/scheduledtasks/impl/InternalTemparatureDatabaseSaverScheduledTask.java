package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.scheduledtasks.DatabaseSaverScheduledTask;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;
import com.piotrmajcher.piwind.services.utils.TemperatureReader;
import com.piotrmajcher.piwind.services.utils.exceptions.TemperatureReaderException;

@Component
public class InternalTemparatureDatabaseSaverScheduledTask implements DatabaseSaverScheduledTask{

    private static final Logger logger = Logger.getLogger(InternalTemparatureDatabaseSaverScheduledTask.class);

    private static final String INFO_FETCHED_INTERNAL_TEMPERATURE_DATA = "Fetched internal temperature data:";
    private static final String ERROR_SCHEDULED_TASK_FAILED = "Failed to execute temperature scheduled task:";

    @Autowired
    private InternalTemperatureRepository internalTemperatureRepository;

    @Autowired
    private TemperatureReader temperatureReader;

    @Scheduled(fixedRate = 5 * 60000)
    @Override
    public void fetchDataAndSaveToDatabase() {

        try {
            saveInternalTemperature();
        } catch (ScheduledTaskException e) {
            logger.error(ERROR_SCHEDULED_TASK_FAILED + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveInternalTemperature() throws ScheduledTaskException {
        Double temperature = null;
        try {
            temperature = temperatureReader.fetchInternalTemperature();
        } catch (TemperatureReaderException e) {
            throw new ScheduledTaskException(e.getMessage());
        }
        InternalTemperature internalTemperature = new InternalTemperature();
        internalTemperature.setTemperatureCelsius(temperature);
        internalTemperatureRepository.save(internalTemperature);
        logger.debug(INFO_FETCHED_INTERNAL_TEMPERATURE_DATA + " " + internalTemperature.getTemperatureCelsius());
    }
}
