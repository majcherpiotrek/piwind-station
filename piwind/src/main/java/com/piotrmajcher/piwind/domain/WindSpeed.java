package com.piotrmajcher.piwind.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wind_speed")
public class WindSpeed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "wind_speed_mps")
    private Double windSpeedMPS;

    @Column(name = "measurement_time_seconds")
    private Integer measurementTimeSeconds;

    @Column(name="date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name="time")
    @Temporal(TemporalType.TIME)
    private Date time;

    @Column(name = "timestamp", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = new Date();
        date = timestamp;
        time = timestamp;
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

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
