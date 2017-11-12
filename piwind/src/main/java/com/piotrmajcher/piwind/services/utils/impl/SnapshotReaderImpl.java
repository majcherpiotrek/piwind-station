package com.piotrmajcher.piwind.services.utils.impl;

import com.piotrmajcher.piwind.domain.Snapshot;
import com.piotrmajcher.piwind.services.utils.exceptions.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.CommandExecutor;
import com.piotrmajcher.piwind.services.utils.SnapshotReader;
import com.piotrmajcher.piwind.services.utils.exceptions.SnapshotReaderException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SnapshotReaderImpl implements SnapshotReader {

    private static final Logger logger = Logger.getLogger(SnapshotReaderImpl.class);

    private static final String TAKE_SNAPSHOT_COMMAND = "raspistill --nopreview -t 200 -w 640 -h 480 -q 75 -o";
    private static final String SNAPSHOT_FILENAME_PREFIX = "./snapshots/snapshot_"; //snapshot filename : snapshot_2017-09-21T12:56:23.5.jpg
    private static final String RM_COMMAND = "rm ";
    private static final String INFO_DELETED_TEMPORARY_SNAPSHOT_FILE = "Deleted temporary snapshot file from the filesystem";

    @Autowired
    private CommandExecutor commandExecutor;

    @Override
    public Snapshot fetchSnapshot() throws SnapshotReaderException {
        Snapshot snapshot;
        try {
            String snapshotFilename = takeSnapshot();
            snapshot = new Snapshot();
            Path path = Paths.get(snapshotFilename);

            byte[] snapshotRawData = Files.readAllBytes(path);
            snapshot.setFilename(snapshotFilename);
            snapshot.setSnapshotImage(snapshotRawData);
            deleteSnapshotFile(snapshotFilename);
            logger.debug(INFO_DELETED_TEMPORARY_SNAPSHOT_FILE);
        } catch (CommandExecutionException | IOException e) {
            throw new SnapshotReaderException(e.getMessage());
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
