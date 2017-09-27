package com.piotrmajcher.piwind.services.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.extensions.compactnotation.CompactConstructor;

import com.piotrmajcher.piwind.domain.Snapshot;
import com.piotrmajcher.piwind.repositories.SnapshotRepository;
import com.piotrmajcher.piwind.services.WebcamService;


@Component
public class WebcamServiceImpl implements WebcamService{
	
	private static final Logger logger = Logger.getLogger(WebcamServiceImpl.class);

	private static final String RM_COMMAND = "rm ";
	private static final String TAKE_PICTURE_COMMAND = "raspistill -o ";
	private static final String SNAPSHOT_FILENAME_PREFIX = "./snapshots/snapshot_"; //snapshot filename : snapshot_2017-09-21T12:56:23.5.jpg
	private static final String SNAPSHOT_SAVED_MESSAGE = "Snapshot saved: ";
	private static final String ERROR_TAKING_SNAPSHOT = "Error occured while taking the snapshot: ";
	private static final String ERROR_DELETING_SNAPSHOT_FILE = "Error occured while deleting the snapshot temporary file: ";
	
	@Autowired
	private SnapshotRepository snapshotRepository;
	
    @Transactional
    @Scheduled(fixedRate = 60000, initialDelay = 5000)
    private void takePictureAndSaveToDB() {
    	try {
    		final String snapshotFilename = takePicture();
    		Path path = Paths.get(snapshotFilename);
    		byte[] snapshotData = Files.readAllBytes(path);
    	
    		Snapshot snapshot = new Snapshot();
    		snapshot.setFilename(snapshotFilename);
    		snapshot.setSnapshotImage(snapshotData);
    		
    		Integer id = snapshotRepository.save(snapshot).getId();
    		logger.info(SNAPSHOT_SAVED_MESSAGE + snapshotFilename);
    		
    		deleteISnapshotFile(snapshotFilename);	
    	} catch(Exception e) {
    		logger.error(e.getMessage());
    	}
    }
    
    private String takePicture() throws Exception {
    	final String snapshotFilename = getSnapshotFilename();
    	final String command = TAKE_PICTURE_COMMAND + snapshotFilename;
    	
    	Process process = Runtime.getRuntime().exec(command);
		 
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    	
		String error = stdError.readLine();
		if (error != null) {
			throw new Exception(ERROR_TAKING_SNAPSHOT + error);
		}
		return snapshotFilename;
    }

	private String getSnapshotFilename() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
    	final Date now = new Date();
    	return SNAPSHOT_FILENAME_PREFIX + dateFormat.format(now);
	}
	
	private void deleteISnapshotFile(String snapshotFilename) throws Exception {
		final String command = RM_COMMAND + snapshotFilename;
		
		Process process = Runtime.getRuntime().exec(command);
		 
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    	
		String error = stdError.readLine();
		if (error != null) {
			throw new Exception(ERROR_DELETING_SNAPSHOT_FILE + error);
		}
	}

	@Override
	public Snapshot getLatestSnapshot() throws Exception {
		//TODO what if findByDate returns null/empty list
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
		List<Snapshot> todaySnapshots = snapshotRepository.findByDateOrderByIdDesc(dateWithoutTime);
		return todaySnapshots.get(0);	
	}

	@Override
	public Iterable<Snapshot> getAllSnapshots() {
		return snapshotRepository.findAll();
	}
}
