package com.piotrmajcher.piwind.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.InternalTemperatureService;
import com.piotrmajcher.piwind.services.utils.EntityToTOConverter;
import com.piotrmajcher.piwind.services.utils.impl.InternalTemperatureEntityConverter;
import com.piotrmajcher.piwind.tos.TemperatureTO;

@Component
public class TemperatureServiceImpl implements InternalTemperatureService {
	
	private static final Logger logger = Logger.getLogger(TemperatureServiceImpl.class);

	private final InternalTemperatureRepository internalTemperatureRepository;
	
	private final EntityToTOConverter<InternalTemperature, TemperatureTO> internalTempConverter;

	@Autowired
	public TemperatureServiceImpl(InternalTemperatureRepository internalTemperatureRepository) {
		this.internalTemperatureRepository = internalTemperatureRepository;
		this.internalTempConverter = new InternalTemperatureEntityConverter();
	}

	@Override
	public List<TemperatureTO> getAllInternalTemperatureData() {
		return internalTempConverter.entityToTransferObject(internalTemperatureRepository.findAll());
	}

	@Override
	public TemperatureTO getLastInternalTemperatureMeasurement() {
		return internalTempConverter.entityToTransferObject(internalTemperatureRepository.findLastMeasurement());
	}
}
