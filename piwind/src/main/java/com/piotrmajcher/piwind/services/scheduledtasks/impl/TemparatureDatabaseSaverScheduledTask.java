package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
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
public class TemparatureDatabaseSaverScheduledTask implements DatabaseSaverScheduledTask{

    private static final Logger logger = Logger.getLogger(TemparatureDatabaseSaverScheduledTask.class);

    private static final String FETCH_INTERNAL_TEMP_COMMAND = "python ./scripts/read_internal_temperature.py";
    private static final String FETCH_EXTERNAL_TEMP_COMMAND = "python ./scripts/read_external_temperature.py";
    private static final String FETCHED_NULL_VALUE = "Received null value while trying to fetch the temperature data";
    private static final String INFO_FETCHED_INTERNAL_TEMPERATURE_DATA = "Fetched internal temperature data:";
    private static final String INFO_FETCHED_EXTERNAL_TEMPERATURE_DATA = "Fetched external temperature data:";
    private static final String ERROR_COMMAND_FAILED = "Failed to execute fetch temperature command:";
    private static final String ERROR_SCHEDULED_TASK_FAILED = "Failed to execute temperature scheduled task:";

    @Autowired
    CommandExecutor commandExecutor;

    @Autowired
    InternalTemperatureRepository internalTemperatureRepository;

    @Autowired
    ExternalTemperatureRepository externalTemperatureRepository;

    @Scheduled(fixedRate = 30000)
    @Override
    public void fetchDataAndSaveToDatabase() {

        try {
            saveInternalTemperature();
            saveExternalTemperature();
        } catch (CommandExecutionException e) {
            logger.error(ERROR_COMMAND_FAILED + " " + e.getMessage());
            logger.error(e.getStackTrace());
        } catch (ScheduledTaskException e) {
            logger.error(ERROR_SCHEDULED_TASK_FAILED + " " + e.getMessage());
            logger.error(e.getStackTrace());
        }
    }

    private void saveExternalTemperature() throws CommandExecutionException, ScheduledTaskException {
        ExternalTemperature externalTemperature = fetchExternalTemperature();
        externalTemperatureRepository.save(externalTemperature);
        logger.info(INFO_FETCHED_EXTERNAL_TEMPERATURE_DATA + " " + externalTemperature.getTemperatureCelsius());
    }

    private void saveInternalTemperature() throws CommandExecutionException, ScheduledTaskException {
        InternalTemperature internalTemperature = fetchInternalTemperature();
        internalTemperatureRepository.save(internalTemperature);
        logger.info(INFO_FETCHED_INTERNAL_TEMPERATURE_DATA + " " + internalTemperature.getTemperatureCelsius());
    }

    private InternalTemperature fetchInternalTemperature() throws CommandExecutionException, ScheduledTaskException {
        InternalTemperature internalTemperature = new InternalTemperature();
        List<String> commandResultList = commandExecutor.executeCommand(FETCH_INTERNAL_TEMP_COMMAND);
        if (commandResultList == null || commandResultList.size() != 1) {
            throw new ScheduledTaskException(FETCHED_NULL_VALUE);
        } else {
            Double temperatureCelsius = Double.valueOf(commandResultList.get(0));
            internalTemperature.setTemperatureCelsius(temperatureCelsius);
        }
        return internalTemperature;
    }

    private ExternalTemperature fetchExternalTemperature() throws CommandExecutionException, ScheduledTaskException {

        ExternalTemperature externalTemperature = new ExternalTemperature();
        List<String> commandResultList = commandExecutor.executeCommand(FETCH_EXTERNAL_TEMP_COMMAND);
        if (commandResultList == null || commandResultList.size() != 1) {
            throw new ScheduledTaskException(FETCHED_NULL_VALUE);
        } else {
            Double temperatureCelsius = Double.valueOf(commandResultList.get(0));
            externalTemperature.setTemperatureCelsius(temperatureCelsius);
        }
        return externalTemperature;
    }
}
