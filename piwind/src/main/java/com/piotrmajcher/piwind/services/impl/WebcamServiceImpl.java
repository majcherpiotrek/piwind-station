package com.piotrmajcher.piwind.services.impl;


import com.piotrmajcher.piwind.domain.Snapshot;
import com.piotrmajcher.piwind.repositories.SnapshotRepository;
import com.piotrmajcher.piwind.services.WebcamService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class WebcamServiceImpl implements WebcamService{
	
	private static final Logger logger = Logger.getLogger(WebcamServiceImpl.class);
	
	private final SnapshotRepository snapshotRepository;

	@Autowired
	public WebcamServiceImpl(SnapshotRepository snapshotRepository) {
		this.snapshotRepository = snapshotRepository;
	}

	@Override
	public Snapshot getLatestSnapshot() {
		return snapshotRepository.findLastSnapshot();
	}
}
