package com.example.tripit.report.contriller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripit.report.entity.Report;
import com.example.tripit.report.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    //모든 신고대상자확인
    @GetMapping
    public List<Report> getAllReports() {
        return reportService.findAll();
    }
    
    //나의 신고대상자 확인
    @GetMapping("/{id}")
    public Report getReportById(@PathVariable int id) {
        return reportService.findById(id);
    }
    
    //신고추가
    @PostMapping
    public ResponseEntity<String> addReport(@RequestBody Report report) {
    	reportService.addReport(report.getUserId());
    	
        return ResponseEntity.ok("신고가 완료되었습니다.");
    }

    
//    @PostMapping("/{id}")
//    public Report updateReport(@PathVariable int id, @RequestBody Report report) {
//        Report existingReport = reportService.findById(id);
//        if (existingReport != null) {
//            // 업데이트할 보고서 객체의 필드들을 설정
//            existingReport.setUserId(report.getUserId());
//            existingReport.setPostId(report.getPostId());
//            existingReport.setReportDetail(report.getReportDetail());
//            existingReport.setReportDate(report.getReportDate());
//            existingReport.setReportFalse(report.getReportFalse());
//
//            // reportService를 통해 보고서를 저장(또는 업데이트)
//            return reportService.addReport(existingReport);
//        } else {
//            // 해당 ID를 가진 보고서가 없을 경우 null 반환
//            return null;
//        }
//    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable int id) {
        try {
            reportService.deleteById(id);
            return ResponseEntity.ok("ID가 " + id + "인 보고서가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ID가 " + id + "인 보고서 삭제에 실패했습니다: " + e.getMessage());
        }
    }

}
