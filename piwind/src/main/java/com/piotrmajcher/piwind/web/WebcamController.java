package com.piotrmajcher.piwind.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.piotrmajcher.piwind.services.WebcamService;

@RestController
@RequestMapping("/webcam")
@CrossOrigin
public class WebcamController {

	@Autowired
	WebcamService webcamService;
	
	@GetMapping(path="/latest-snap", produces = MediaType.IMAGE_JPEG_VALUE)
	@CrossOrigin
	public @ResponseBody byte[] getLatestSnapshot() throws Exception {
		return webcamService.getLatestSnapshot().getSnapshotImage();
	}
}
