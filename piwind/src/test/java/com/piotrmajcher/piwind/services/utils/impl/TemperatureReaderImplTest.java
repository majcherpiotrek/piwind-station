package com.piotrmajcher.piwind.services.utils.impl;

import com.piotrmajcher.piwind.services.utils.exceptions.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.CommandExecutor;
import com.piotrmajcher.piwind.services.utils.exceptions.TemperatureReaderException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TemperatureReaderImplTest {

    private static final String FETCH_INTERNAL_TEMP_COMMAND = "python ./scripts/read_internal_temperature.py";
    private static final String FETCH_EXTERNAL_TEMP_COMMAND = "python ./scripts/read_external_temperature.py";
    private static final Double TEMPERATURE = 25.0;

    @InjectMocks
    private TemperatureReaderImpl temperatureReader;

    @Mock
    CommandExecutor commandExecutor;

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchInternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorExceptionThrown() throws Exception{
        Mockito.when(commandExecutor.executeCommand(FETCH_INTERNAL_TEMP_COMMAND)).thenThrow(CommandExecutionException.class);
        temperatureReader.fetchInternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchExternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorExceptionThrown() throws Exception{
        Mockito.when(commandExecutor.executeCommand(FETCH_EXTERNAL_TEMP_COMMAND)).thenThrow(CommandExecutionException.class);
        temperatureReader.fetchExternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchInternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsNull() throws Exception {
        Mockito.when(commandExecutor.executeCommand(FETCH_INTERNAL_TEMP_COMMAND)).thenReturn(null);
        temperatureReader.fetchInternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchExternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsNull() throws Exception {
        Mockito.when(commandExecutor.executeCommand(FETCH_EXTERNAL_TEMP_COMMAND)).thenReturn(null);
        temperatureReader.fetchExternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchInternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsEmptyList() throws Exception {
        Mockito.when(commandExecutor.executeCommand(FETCH_INTERNAL_TEMP_COMMAND)).thenReturn(new LinkedList<>());
        temperatureReader.fetchInternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchExternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsEmptyList() throws Exception {
        Mockito.when(commandExecutor.executeCommand(FETCH_EXTERNAL_TEMP_COMMAND)).thenReturn(new LinkedList<>());
        temperatureReader.fetchExternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchInternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsListLongerThenOneElement() throws Exception {
        List<String> resultsList = new LinkedList<>();
        resultsList.add("1.0");
        resultsList.add("2.0");
        Mockito.when(commandExecutor.executeCommand(FETCH_INTERNAL_TEMP_COMMAND)).thenReturn(resultsList);
        temperatureReader.fetchInternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchExternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsListLongerThenOneElement() throws Exception {
        List<String> resultsList = new LinkedList<>();
        resultsList.add("1.0");
        resultsList.add("2.0");
        Mockito.when(commandExecutor.executeCommand(FETCH_EXTERNAL_TEMP_COMMAND)).thenReturn(resultsList);
        temperatureReader.fetchExternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchInternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsListWithIncorrectValues() throws Exception {
        List<String> resultsList = new LinkedList<>();
        resultsList.add("text");
        Mockito.when(commandExecutor.executeCommand(FETCH_INTERNAL_TEMP_COMMAND)).thenReturn(resultsList);
        temperatureReader.fetchInternalTemperature();
    }

    @Test(expected = TemperatureReaderException.class)
    public void fetchExternalTempShouldThrowTemperatureReaderExceptionWhenCommandExecutorReturnsListWithIncorrectValues() throws Exception {
        List<String> resultsList = new LinkedList<>();
        resultsList.add("text");
        Mockito.when(commandExecutor.executeCommand(FETCH_EXTERNAL_TEMP_COMMAND)).thenReturn(resultsList);
        temperatureReader.fetchExternalTemperature();
    }

    @Test
    public void fetchInternalTempShouldReturnCorrectTemperature() throws Exception {
        List<String> resultsList = new LinkedList<>();
        resultsList.add(TEMPERATURE.toString());
        Mockito.when(commandExecutor.executeCommand(FETCH_INTERNAL_TEMP_COMMAND)).thenReturn(resultsList);
        assertEquals(TEMPERATURE, temperatureReader.fetchInternalTemperature().getTemperatureCelsius(), 0);
    }

    @Test
    public void fetchExternalTempShouldReturnCorrectTemperature() throws Exception {
        List<String> resultsList = new LinkedList<>();
        resultsList.add(TEMPERATURE.toString());
        Mockito.when(commandExecutor.executeCommand(FETCH_EXTERNAL_TEMP_COMMAND)).thenReturn(resultsList);
        assertEquals(TEMPERATURE, temperatureReader.fetchExternalTemperature().getTemperatureCelsius(), 0);
    }




}