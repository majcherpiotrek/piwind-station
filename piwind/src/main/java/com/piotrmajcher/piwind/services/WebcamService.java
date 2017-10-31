package com.piotrmajcher.piwind.services;

import com.piotrmajcher.piwind.domain.Snapshot;

public interface WebcamService {
	
	public Snapshot getLatestSnapshot() throws Exception;
}
