package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.services.utils.exceptions.TemperatureReaderException;

public interface TemperatureReader {

    InternalTemperature fetchInternalTemperature() throws TemperatureReaderException;

    ExternalTemperature fetchExternalTemperature() throws TemperatureReaderException;
}
