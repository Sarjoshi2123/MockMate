package com.mock.interview.dto;

import java.util.Map;

public class DashboardStats {
    private Long totalInterviews;
    private Double averageScore;
    private Long completedInterviews;
    private Map<String, Long> techStackCounts;

    public DashboardStats(Long totalInterviews, Double averageScore, Long completedInterviews, Map<String, Long> techStackCounts) {
        this.totalInterviews = totalInterviews;
        this.averageScore = averageScore;
        this.completedInterviews = completedInterviews;
        this.techStackCounts = techStackCounts;
    }

    // Getters and Setters
    public Long getTotalInterviews() { return totalInterviews; }
    public void setTotalInterviews(Long totalInterviews) { this.totalInterviews = totalInterviews; }

    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }

    public Long getCompletedInterviews() { return completedInterviews; }
    public void setCompletedInterviews(Long completedInterviews) { this.completedInterviews = completedInterviews; }

    public Map<String, Long> getTechStackCounts() { return techStackCounts; }
    public void setTechStackCounts(Map<String, Long> techStackCounts) { this.techStackCounts = techStackCounts; }
}
