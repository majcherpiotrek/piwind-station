package com.piotrmajcher.piwind.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class WindSpeedTO {

	private double windSpeedMPS;
	
	private int measurementTimeSeconds;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dateTime;

	public double getWindSpeedMPS() {
		return windSpeedMPS;
	}

	public void setWindSpeedMPS(double windSpeedMPS) {
		this.windSpeedMPS = windSpeedMPS;
	}

	public int getMeasurementTimeSeconds() {
		return measurementTimeSeconds;
	}

	public void setMeasurementTimeSeconds(int measurementTimeSeconds) {
		this.measurementTimeSeconds = measurementTimeSeconds;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
