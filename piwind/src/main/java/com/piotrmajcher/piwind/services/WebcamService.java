package com.piotrmajcher.piwind.services;

public interface WebcamService {
	
	public byte[] getLatestSnapshot() throws Exception;
}
