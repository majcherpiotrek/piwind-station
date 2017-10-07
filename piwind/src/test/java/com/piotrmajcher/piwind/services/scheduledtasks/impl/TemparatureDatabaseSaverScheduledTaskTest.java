package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.utils.TemperatureReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TemparatureDatabaseSaverScheduledTaskTest {
    private static final Double INTERNAL_TEMP = 25.0;
    private static final Double EXTERNAL_TEMP = 18.0;

    @InjectMocks
    private TemparatureDatabaseSaverScheduledTask temparatureDatabaseSaverScheduledTask;

    @Mock
    private InternalTemperatureRepository internalTemperatureRepositoryMock;

    @Mock
    private ExternalTemperatureRepository externalTemperatureRepositoryMock;

    @Mock
    private TemperatureReader temperatureReader;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchDataAndSaveToDBWhenBothCommandsReturnCorrectValues() throws Exception {
        InternalTemperature internalTemperature = new InternalTemperature();
        internalTemperature.setTemperatureCelsius(INTERNAL_TEMP);

        ExternalTemperature externalTemperature = new ExternalTemperature();
        externalTemperature.setTemperatureCelsius(EXTERNAL_TEMP);

        Mockito.when(temperatureReader.fetchInternalTemperature()).thenReturn(internalTemperature);
        Mockito.when(temperatureReader.fetchExternalTemperature()).thenReturn(externalTemperature);

        temparatureDatabaseSaverScheduledTask.fetchDataAndSaveToDatabase();

        Mockito.verify(internalTemperatureRepositoryMock, Mockito.times(1)).save(internalTemperature);
        Mockito.verify(externalTemperatureRepositoryMock, Mockito.times(1)).save(externalTemperature);
    }
}