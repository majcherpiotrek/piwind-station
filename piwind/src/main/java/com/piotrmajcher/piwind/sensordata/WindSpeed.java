package com.piotrmajcher.piwind.sensordata;

import com.piotrmajcher.piwind.enums.WindDirection;

public class WindSpeed {

    private Double windSpeedMPS;
    
    private WindDirection windDirection;

    private Integer measurementTimeSeconds;

    public Double getWindSpeedMPS() {
        return windSpeedMPS;
    }

    public void setWindSpeedMPS(Double windSpeedMPS) {
        this.windSpeedMPS = windSpeedMPS;
    }
    
    public WindDirection getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(WindDirection windDirection) {
		this.windDirection = windDirection;
	}

	public Integer getMeasurementTimeSeconds() {
        return measurementTimeSeconds;
    }

    public void setMeasurementTimeSeconds(Integer measurementTimeSeconds) {
        this.measurementTimeSeconds = measurementTimeSeconds;
    }
}
