package com.piotrmajcher.piwind.services;


import com.piotrmajcher.piwind.domain.WindSpeed;

public interface WindSpeedService {

    public WindSpeed getLatestWindSpeedMeasurement() throws Exception;

    public Iterable<WindSpeed> getAllWindSpeedMeasurements();
}
