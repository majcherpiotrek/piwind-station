package com.piotrmajcher.piwind.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table
public class ExternalTemperature {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column
	private Double temperatureCelsius;
	
	@Column
	private LocalDate date;
	
	@Column
	private LocalDateTime dateTime;
	
	@PrePersist
	protected void onCreate() {
		dateTime = LocalDateTime.now();
		date = dateTime.toLocalDate();
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

	public LocalDate getDate() {
		return date;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
}
