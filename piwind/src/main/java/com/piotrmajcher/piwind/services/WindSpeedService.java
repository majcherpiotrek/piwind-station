package com.piotrmajcher.piwind.services;


import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.tos.WindSpeedTO;

import java.util.List;

public interface WindSpeedService {

    WindSpeedTO getLatestWindSpeedMeasurement();

    List<WindSpeedTO> getAllWindSpeedMeasurements();

    List<WindSpeedTO> getWindSpeedMeasurementsFromLastXMinutes(int minutes);

    Double getAverageWindSpeedFromLastXMinutesKnots(int minutes);

    Double getAverageWindSpeedFromLastXMinutesMps(int minutes);

    Double getAverageWindSpeedFromLastXMinutesKmh(int minutes);
}
