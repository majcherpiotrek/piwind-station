package com.piotrmajcher.piwind.services.utils.impl;

import java.util.LinkedList;
import java.util.List;

import com.piotrmajcher.piwind.domain.InternalTemperature;
import com.piotrmajcher.piwind.services.utils.EntityToTOConverter;
import com.piotrmajcher.piwind.tos.TemperatureTO;

public class InternalTemperatureEntityConverter implements EntityToTOConverter<InternalTemperature, TemperatureTO>{
	
	@Override
	public TemperatureTO entityToTransferObject(InternalTemperature entity) {
		TemperatureTO to = null;
		if (entity != null) {
			to = new TemperatureTO();
			to.setTemperatureCelsius(entity.getTemperatureCelsius());
			to.setDateTime(entity.getDateTime());
		}
		return to;
	}

	@Override
	public List<TemperatureTO> entityToTransferObject(List<InternalTemperature> entityList) {
		List<TemperatureTO> toList = null;
		if (entityList != null) {
			toList = new LinkedList<>();
			for (InternalTemperature entity : entityList) {
				toList.add(entityToTransferObject(entity));
			}
		}
		return toList;
	}
}
