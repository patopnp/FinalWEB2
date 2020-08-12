package edu.uces.ar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.uces.ar.model.dto.CartDTO;
import edu.uces.ar.model.dto.ReportDTO;

@Service
public interface ProcessCartsService {

	List<CartDTO> getAllCarts();
	
	ReportDTO processCarts();
	
	List<ReportDTO> getAllReports();

	List<ReportDTO> getReports(String from, String to);
}
