package com.piotrmajcher.piwind.services.impl;


import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.services.WebcamService;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;
import com.piotrmajcher.piwind.services.utils.SnapshotReader;
import com.piotrmajcher.piwind.services.utils.exceptions.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.exceptions.SnapshotReaderException;


@Component
public class WebcamServiceImpl implements WebcamService{
	
	private static final Logger logger = Logger.getLogger(WebcamServiceImpl.class);
	private static final String INFO_SNAPSHOT_SAVED = "Snapshot saved:";
    private static final String ERROR_COMMAND_FAILED = "Failed to execute take snapshot command:";
    private static final String ERROR_SCHEDULED_TASK_FAILED = "Failed to execute snapshot scheduled task:";
	
	private SnapshotReader snapshotReader;
	
	private LinkedBlockingDeque<byte[]> snapshotsQueue;
	
    
	@Autowired
	public WebcamServiceImpl(SnapshotReader snapshotReader) {
		this.snapshotsQueue = new LinkedBlockingDeque<>();
		this.snapshotReader = snapshotReader;
	}

	@Override
	public byte[] getLatestSnapshot() {
		return snapshotsQueue.peekFirst();
	}
	
	@Scheduled(fixedRate = 2000)
    private void fetchDataAndSaveToDatabase() {
        try {
            saveSnapshot();
        } catch (ScheduledTaskException e) {
            logger.error(ERROR_COMMAND_FAILED + " " + e.getMessage());
            e.printStackTrace();
        } catch (CommandExecutionException e) {
            logger.error(ERROR_SCHEDULED_TASK_FAILED + " " + e.getMessage());
            e.printStackTrace();
        }
    }
		
    private void saveSnapshot() throws CommandExecutionException, ScheduledTaskException {
        String snapshotFilename;
        byte[] snapshot = null;
        try {
            snapshot = snapshotReader.fetchSnapshot();
        } catch (SnapshotReaderException e) {
            throw new ScheduledTaskException(e.getMessage());
        }
       snapshotsQueue.addFirst(snapshot);
       
       while (snapshotsQueue.size() > 1) {
			snapshotsQueue.pollLast();
		}
    }
}
