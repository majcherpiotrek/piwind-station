package com.piotrmajcher.piwind.services.impl;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.repositories.WindSpeedRepository;
import com.piotrmajcher.piwind.services.WindSpeedService;
import com.piotrmajcher.piwind.services.utils.WindSpeedUnitsConverter;
import com.piotrmajcher.piwind.services.utils.impl.WindSpeedUnitsConverterImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WindSpeedServiceImpl implements WindSpeedService{

    private static final Logger logger = Logger.getLogger(WindSpeedServiceImpl.class);

    private final WindSpeedRepository windSpeedRepository;
    private WindSpeedUnitsConverter windSpeedUnitsConverter;

    @Autowired
    public WindSpeedServiceImpl(WindSpeedRepository windSpeedRepository) {
        this.windSpeedRepository = windSpeedRepository;
        this.windSpeedUnitsConverter = new WindSpeedUnitsConverterImpl();
    }

    @Override
    public WindSpeed getLatestWindSpeedMeasurement() {
        return windSpeedRepository.findLastMeasurement();
    }

    @Override
    public List<WindSpeed> getAllWindSpeedMeasurements() {
        return windSpeedRepository.findAll();
    }

    @Override
    public List<WindSpeed> getWindSpeedMeasurementsFromLastXMinutes(int minutes) {
        LocalDateTime fromDate = LocalDateTime.now().minusMinutes(minutes);
        return windSpeedRepository.findAllMeasurementsFromSpecifiedDateTimeTillNow(fromDate);
    }

    @Override
    public Double getAverageWindSpeedFromLastXMinutesKnots(int minutes) {
        LocalDateTime fromDate = LocalDateTime.now().minusMinutes(minutes);
        List<WindSpeed> measurementsList = windSpeedRepository.findAllMeasurementsFromSpecifiedDateTimeTillNow(fromDate);

        return windSpeedUnitsConverter.mpsToKnots(calculateAverageWindSpeedMPS(measurementsList));
    }

    @Override
    public Double getAverageWindSpeedFromLastXMinutesMps(int minutes) {
        LocalDateTime fromDate = LocalDateTime.now().minusMinutes(minutes);
        List<WindSpeed> measurementsList = windSpeedRepository.findAllMeasurementsFromSpecifiedDateTimeTillNow(fromDate);

        return calculateAverageWindSpeedMPS(measurementsList);
    }

    @Override
    public Double getAverageWindSpeedFromLastXMinutesKmh(int minutes) {
        LocalDateTime fromDate = LocalDateTime.now().minusMinutes(minutes);
        List<WindSpeed> measurementsList = windSpeedRepository.findAllMeasurementsFromSpecifiedDateTimeTillNow(fromDate);

        return windSpeedUnitsConverter.mpsToKmh(calculateAverageWindSpeedMPS(measurementsList));
    }

    private double calculateAverageWindSpeedMPS(List<WindSpeed> measurementsList) {
        double sum = 0;
        for (WindSpeed windSpeed : measurementsList) {
            sum += windSpeed.getWindSpeedMPS();
        }
        sum = sum / measurementsList.size();
        return sum;
    }
}
