package com.example.tripit.report.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripit.report.entity.Report;


@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
