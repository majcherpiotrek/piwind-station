package com.piotrmajcher.piwind.services.impl;

import com.piotrmajcher.piwind.domain.ExternalTemperature;
import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.repositories.ExternalTemperatureRepository;
import com.piotrmajcher.piwind.repositories.InternalTemperatureRepository;
import com.piotrmajcher.piwind.services.TemperatureService;
import com.piotrmajcher.piwind.services.utils.EntityToTOConverter;
import com.piotrmajcher.piwind.services.utils.impl.ExternalTemperatureEntityConverter;
import com.piotrmajcher.piwind.services.utils.impl.InternalTemperatureEntityConverter;
import com.piotrmajcher.piwind.tos.TemperatureTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TemperatureServiceImpl implements TemperatureService {
	
	private static final Logger logger = Logger.getLogger(TemperatureServiceImpl.class);

	private final InternalTemperatureRepository internalTemperatureRepository;
	
	private final ExternalTemperatureRepository externalTemperatureRepository;
	
	private final EntityToTOConverter<ExternalTemperature, TemperatureTO> externalTempConverter;
	private final EntityToTOConverter<InternalTemperature, TemperatureTO> internalTempConverter;

	@Autowired
	public TemperatureServiceImpl(InternalTemperatureRepository internalTemperatureRepository, ExternalTemperatureRepository externalTemperatureRepository) {
		this.internalTemperatureRepository = internalTemperatureRepository;
		this.externalTemperatureRepository = externalTemperatureRepository;
		this.externalTempConverter = new ExternalTemperatureEntityConverter();
		this.internalTempConverter = new InternalTemperatureEntityConverter();
	}

	@Override
	public List<TemperatureTO> getAllExternalTemperatureData() {
		return externalTempConverter.entityToTransferObject(externalTemperatureRepository.findAll());
	}

	@Override
	public List<TemperatureTO> getAllInternalTemperatureData() {
		return internalTempConverter.entityToTransferObject(internalTemperatureRepository.findAll());
	}

	@Override
	public TemperatureTO getLastExternalTemperatureMeasurement() {
		return externalTempConverter.entityToTransferObject(externalTemperatureRepository.findLastMeasurement());
	}

	@Override
	public TemperatureTO getLastInternalTemperatureMeasurement() {
		return internalTempConverter.entityToTransferObject(internalTemperatureRepository.findLastMeasurement());
	}
}
