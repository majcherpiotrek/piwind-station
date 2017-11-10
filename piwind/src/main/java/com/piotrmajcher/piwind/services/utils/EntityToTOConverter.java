package com.piotrmajcher.piwind.services.utils;

import java.util.List;

public interface EntityToTOConverter<E, T> {
	
	T entityToTransferObject(E entity);
	List<T> entityToTransferObject(List<E> entityList);
}
