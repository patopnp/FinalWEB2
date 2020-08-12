package edu.uces.ar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.uces.ar.model.dto.ReportDTO;

import edu.uces.ar.service.ProcessCartsService;

@RestController
@Validated
public class ProcessCartsController {
	
	private final ProcessCartsService processCartsService;
	
	public ProcessCartsController(ProcessCartsService processCartsService) {
		super();

		this.processCartsService = processCartsService;
	}


	
	@PostMapping(path = "/batch/processCarts")
	public ResponseEntity<ReportDTO> processCarts() {
		return new ResponseEntity<>(processCartsService.processCarts(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/batch/processCarts")
	public ResponseEntity<List<ReportDTO>> getProcessedCarts(@RequestParam(name = "from", required = false) String from, @RequestParam(name = "to", required = false) String to) {
		
		if(from == null || to == null)
		{
			return new ResponseEntity<>(processCartsService.getAllReports(), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(processCartsService.getReports(from, to), HttpStatus.OK);
		}
		
	}
	
}
