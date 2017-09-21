package com.piotrmajcher.piwind.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "webcam_snapshots")
public class Snapshot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name="time")
	@Temporal(TemporalType.TIME)
	private Date time;
	
	@Column(name = "timestamp", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Column(name = "filename")
	private String filename;
	
	@Lob
	@Column(name = "snapshot_image", columnDefinition="mediumblob")
	private byte[] snapshotImage;

	
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
	
	public Date getDate() {
		return date;
	}
	
	public Date getTime() {
		return time;
	}
	
	public Date getTimestamp() {
		return timestamp;
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
