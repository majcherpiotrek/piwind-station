package com.piotrmajcher.piwind.services.scheduledtasks.impl;

import com.piotrmajcher.piwind.domain.Snapshot;
import com.piotrmajcher.piwind.repositories.SnapshotRepository;
import com.piotrmajcher.piwind.services.scheduledtasks.DatabaseSaverScheduledTask;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;
import com.piotrmajcher.piwind.services.utils.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.CommandExecutor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WebcamSnapshotsDatabaseSaverScheduledTask implements DatabaseSaverScheduledTask{

    private static final Logger logger = Logger.getLogger(WebcamSnapshotsDatabaseSaverScheduledTask.class);

    private static final String TAKE_SNAPSHOT_COMMAND = "raspistill --nopreview -o";
    private static final String SNAPSHOT_FILENAME_PREFIX = "./snapshots/snapshot_"; //snapshot filename : snapshot_2017-09-21T12:56:23.5.jpg
    private static final String RM_COMMAND = "rm ";
    private static final String INFO_SNAPSHOT_SAVED = "Snapshot saved:";
    private static final String ERROR_COMMAND_FAILED = "Failed to execute take snapshot command:";
    private static final String ERROR_SCHEDULED_TASK_FAILED = "Failed to execute snapshot scheduled task:";
    private static final String INFO_SNAPSHOT_TEMPORARY_FILE_DELETED = "Snapshot temporary file deleted from file system: ";

    @Autowired
    CommandExecutor commandExecutor;

    @Autowired
    SnapshotRepository snapshotRepository;

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
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
        String snapshotFilename = takeSnapshot();
        Snapshot snapshot = readSnapshot(snapshotFilename);
        snapshotRepository.save(snapshot);
        logger.info(INFO_SNAPSHOT_SAVED + " " + snapshotFilename);
        deleteSnapshotFile(snapshotFilename);
        logger.info(INFO_SNAPSHOT_TEMPORARY_FILE_DELETED + " " + snapshotFilename);
    }

    private Snapshot readSnapshot(String snapshotFilename) throws ScheduledTaskException {
        Snapshot snapshot = new Snapshot();
        Path path = Paths.get(snapshotFilename);
        try {
            byte[] snapshotRawData = Files.readAllBytes(path);
            snapshot.setFilename(snapshotFilename);
            snapshot.setSnapshotImage(snapshotRawData);
        } catch (IOException e) {
            throw new ScheduledTaskException("Error reading snapshot from file system.");
        }
        return snapshot;
    }

    private String takeSnapshot() throws CommandExecutionException {
        final String snapshotFilename = getSnapshotFilename();
        final String command = TAKE_SNAPSHOT_COMMAND + " " + snapshotFilename;
        commandExecutor.executeCommand(command);
        return snapshotFilename;
    }

    private String getSnapshotFilename() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
        final Date now = new Date();
        return SNAPSHOT_FILENAME_PREFIX + dateFormat.format(now);
    }

    private void deleteSnapshotFile(String snapshotFilename) throws CommandExecutionException {
        final String command = RM_COMMAND + snapshotFilename;
        commandExecutor.executeCommand(command);
    }
}
