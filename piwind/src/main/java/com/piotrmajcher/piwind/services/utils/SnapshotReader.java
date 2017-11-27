package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.services.utils.exceptions.SnapshotReaderException;

public interface SnapshotReader {

    byte[] fetchSnapshot() throws SnapshotReaderException;
}
