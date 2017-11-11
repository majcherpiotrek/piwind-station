package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.services.utils.exceptions.TemperatureReaderException;

public interface TemperatureReader {

    Double fetchInternalTemperature() throws TemperatureReaderException;

    Double fetchExternalTemperature() throws TemperatureReaderException;
}
