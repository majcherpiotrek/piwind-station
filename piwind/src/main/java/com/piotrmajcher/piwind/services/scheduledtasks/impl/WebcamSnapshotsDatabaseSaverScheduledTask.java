package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import com.piotrmajcher.piwind.domain.Snapshot;
import com.piotrmajcher.piwind.repositories.SnapshotRepository;
import com.piotrmajcher.piwind.services.scheduledtasks.DatabaseSaverScheduledTask;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;
import com.piotrmajcher.piwind.services.utils.exceptions.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.SnapshotReader;
import com.piotrmajcher.piwind.services.utils.exceptions.SnapshotReaderException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebcamSnapshotsDatabaseSaverScheduledTask implements DatabaseSaverScheduledTask{

    private static final Logger logger = Logger.getLogger(WebcamSnapshotsDatabaseSaverScheduledTask.class);

    private static final String INFO_SNAPSHOT_SAVED = "Snapshot saved:";
    private static final String ERROR_COMMAND_FAILED = "Failed to execute take snapshot command:";
    private static final String ERROR_SCHEDULED_TASK_FAILED = "Failed to execute snapshot scheduled task:";

    @Autowired
    SnapshotRepository snapshotRepository;

    @Autowired
    private SnapshotReader snapshotReader;

    @Scheduled(fixedRate = 30000, initialDelay = 5000)
    @Override
    public void fetchDataAndSaveToDatabase() {
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
        Snapshot snapshot;
        String snapshotFilename;
        try {
            snapshot = snapshotReader.fetchSnapshot();
            snapshotFilename = snapshot.getFilename();
        } catch (SnapshotReaderException e) {
            throw new ScheduledTaskException(e.getMessage());
        }
        snapshotRepository.save(snapshot);
        logger.info(INFO_SNAPSHOT_SAVED + " " + snapshotFilename);
    }
}
