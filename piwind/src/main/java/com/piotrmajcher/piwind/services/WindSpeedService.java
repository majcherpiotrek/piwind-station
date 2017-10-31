package com.piotrmajcher.piwind.services;


import com.piotrmajcher.piwind.domain.WindSpeed;

import java.util.List;

public interface WindSpeedService {

    WindSpeed getLatestWindSpeedMeasurement();

    List<WindSpeed> getAllWindSpeedMeasurements();

    List<WindSpeed> getWindSpeedMeasurementsFromLastXMinutes(int minutes);

    Double getAverageWindSpeedFromLastXMinutesKnots(int minutes);

    Double getAverageWindSpeedFromLastXMinutesMps(int minutes);

    Double getAverageWindSpeedFromLastXMinutesKmh(int minutes);
}
