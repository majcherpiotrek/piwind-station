package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.domain.Snapshot;

public interface SnapshotReader {

    Snapshot fetchSnapshot() throws SnapshotReaderException;
}
