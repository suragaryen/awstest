package com.example.tripit.report.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "report_detail", length = 50, nullable = false, columnDefinition = "VARCHAR(50) DEFAULT ''")
    private String reportDetail = ""; // 기본값으로 초기화

    @Column(name = "report_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // 날짜와 시간을 포함한 타임스탬프로 설정
    private Date reportDate;

    @Column(name = "report_false", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private int reportFalse;

    public Report() {
        this.reportDate = new Date(); // 현재 시간으로 초기화
    }

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(String reportDetail) {
        this.reportDetail = reportDetail;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getReportFalse() {
        return reportFalse;
    }

    public void setReportFalse(int reportFalse) {
        this.reportFalse = reportFalse;
    }
}
