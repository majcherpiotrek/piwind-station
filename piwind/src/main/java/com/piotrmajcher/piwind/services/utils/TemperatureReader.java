package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;

public interface TemperatureReader {

    InternalTemperature fetchInternalTemperature() throws TemperatureReaderException;

    ExternalTemperature fetchExternalTemperature() throws TemperatureReaderException;
}
