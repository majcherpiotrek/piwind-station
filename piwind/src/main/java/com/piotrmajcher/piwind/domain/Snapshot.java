package com.piotrmajcher.piwind.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "webcam_snapshots")
public class Snapshot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private LocalDate date;
	
	@Column
	private LocalDateTime dateTime;
	
	@Column
	private String filename;
	
	@Lob
	@Column(name = "snapshot_image", columnDefinition="mediumblob")
	private byte[] snapshotImage;

	
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
	
	public LocalDate getDate() {
		return date;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getSnapshotImage() {
		return snapshotImage;
	}

	public void setSnapshotImage(byte[] snapshotImage) {
		this.snapshotImage = snapshotImage;
	}
}
