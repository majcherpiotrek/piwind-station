package com.piotrmajcher.piwind.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "wind_speed")
public class WindSpeed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column
    private Double windSpeedMPS;

    @Column
    private Integer measurementTimeSeconds;

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

    public Double getWindSpeedMPS() {
        return windSpeedMPS;
    }

    public void setWindSpeedMPS(Double windSpeedMPS) {
        this.windSpeedMPS = windSpeedMPS;
    }

    public Integer getMeasurementTimeSeconds() {
        return measurementTimeSeconds;
    }

    public void setMeasurementTimeSeconds(Integer measurementTimeSeconds) {
        this.measurementTimeSeconds = measurementTimeSeconds;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
