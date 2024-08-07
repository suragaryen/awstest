package com.example.tripit.report.service;

import com.example.tripit.report.entity.Report;
import com.example.tripit.report.repository.ReportRepository;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(int id) {
        return reportRepository.findById(id).orElse(null);
    }

    public Report addReport(int userId) {
    	Report report = new Report();
    	report.setUserId(userId);
        return reportRepository.save(report);
    }
    

    public void deleteById(int id) {
        reportRepository.deleteById(id);
    }
}
