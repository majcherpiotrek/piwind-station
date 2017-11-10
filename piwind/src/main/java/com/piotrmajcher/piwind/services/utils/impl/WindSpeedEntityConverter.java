package com.piotrmajcher.piwind.services.utils.impl;

import java.util.LinkedList;
import java.util.List;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.services.utils.EntityToTOConverter;
import com.piotrmajcher.piwind.tos.WindSpeedTO;

public class WindSpeedEntityConverter implements EntityToTOConverter<WindSpeed, WindSpeedTO>{
	
	@Override
	public WindSpeedTO entityToTransferObject(WindSpeed entity) {
		WindSpeedTO to = null;
		if (entity != null) {
			to = new WindSpeedTO();
			to.setWindSpeedMPS(entity.getWindSpeedMPS());
			to.setMeasurementTimeSeconds(entity.getMeasurementTimeSeconds());
			to.setDateTime(entity.getDateTime());
		}
		return to;
	}

	@Override
	public List<WindSpeedTO> entityToTransferObject(List<WindSpeed> entityList) {
		List<WindSpeedTO> toList = null;
		if (entityList != null) {
			toList = new LinkedList<>();
			for (WindSpeed entity : entityList) {
				toList.add(entityToTransferObject(entity));
			}
		}
		return toList;
	}
}
