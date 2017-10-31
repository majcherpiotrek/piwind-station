package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.domain.Snapshot;
import com.piotrmajcher.piwind.services.utils.exceptions.SnapshotReaderException;

public interface SnapshotReader {

    Snapshot fetchSnapshot() throws SnapshotReaderException;
}
