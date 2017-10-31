package com.piotrmajcher.piwind.services.impl;


import com.piotrmajcher.piwind.domain.Snapshot;
import com.piotrmajcher.piwind.repositories.SnapshotRepository;
import com.piotrmajcher.piwind.services.WebcamService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Component
public class WebcamServiceImpl implements WebcamService{
	
	private static final Logger logger = Logger.getLogger(WebcamServiceImpl.class);
	
	@Autowired
	private SnapshotRepository snapshotRepository;

	@Override
	public Snapshot getLatestSnapshot() {
		return snapshotRepository.findLastSnapshot(LocalDate.now());
	}
}
