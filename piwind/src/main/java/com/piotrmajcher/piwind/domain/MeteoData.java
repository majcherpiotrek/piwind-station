package com.piotrmajcher.piwind.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.piotrmajcher.piwind.enums.WindDirection;

@Entity
@Table
public class MeteoData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column
	private Double temperatureCelsius;
	
	@Enumerated(EnumType.STRING)
	private WindDirection windDirection;
	
	@Column
    private Double windSpeedMPS;

    @Column
    private Integer windSpeedMeasurementTimeSeconds;
		
	@Column
	private LocalDateTime dateTime;
	
	@PrePersist
	protected void onCreate() {
		dateTime = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getTemperatureCelsius() {
		return temperatureCelsius;
	}

	public void setTemperatureCelsius(Double temperatureCelsius) {
		this.temperatureCelsius = temperatureCelsius;
	}
	
	public WindDirection getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(WindDirection windDirection) {
		this.windDirection = windDirection;
	}

	public Double getWindSpeedMPS() {
		return windSpeedMPS;
	}

	public void setWindSpeedMPS(Double windSpeedMPS) {
		this.windSpeedMPS = windSpeedMPS;
	}

	public Integer getWindSpeedMeasurementTimeSeconds() {
		return windSpeedMeasurementTimeSeconds;
	}

	public void setWindSpeedMeasurementTimeSeconds(Integer windSpeedMeasurementTimeSeconds) {
		this.windSpeedMeasurementTimeSeconds = windSpeedMeasurementTimeSeconds;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}
}
