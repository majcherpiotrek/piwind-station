package com.piotrmajcher.piwind.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.piotrmajcher.piwind.services.WebcamService;

@Controller
@RequestMapping(path="/webcam")
@CrossOrigin
public class WebcamController {

	@Autowired
	WebcamService webcamService;
	
	@GetMapping(path="/latest-snap", produces = MediaType.IMAGE_JPEG_VALUE)
	@CrossOrigin
	public @ResponseBody byte[] getLatestSnapshot() {
		return webcamService.getLatestSnapshot().getSnapshotImage();
	}
}
