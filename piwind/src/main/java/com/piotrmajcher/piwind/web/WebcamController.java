package com.piotrmajcher.piwind.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.piotrmajcher.piwind.services.WebcamService;

@RestController
@RequestMapping("/webcam")
@CrossOrigin
@Api(
		description = "\nEndpoint for getting the webcam snapshots from the station."
)
public class WebcamController {

	private final WebcamService webcamService;

	@Autowired
	public WebcamController(WebcamService webcamService) {
		this.webcamService = webcamService;
	}

	@GetMapping(path="/latest-snap", produces = MediaType.IMAGE_JPEG_VALUE)
	@CrossOrigin
	@ApiOperation( value = "Returns the latest scnapshot taken by the webcam.")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Successfully fetched the latest snapshot.")
	})
	public @ResponseBody byte[] getLatestSnapshot() throws Exception {
		return webcamService.getLatestSnapshot().getSnapshotImage();
	}
}
