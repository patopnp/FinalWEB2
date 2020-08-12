package edu.uces.ar.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.uces.ar.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	
}
