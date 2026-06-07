package com.mock.interview.dto;

import java.util.Date;
import java.util.List;

public class InterviewDetailsResponse {
    private Long id;
    private String techStack;
    private String experienceLevel;
    private Integer numQuestions;
    private Boolean completed;
    private Double overallScore;
    private Date createdAt;
    private List<QuestionFeedbackDto> questions;

    public InterviewDetailsResponse(Long id, String techStack, String experienceLevel, 
                                    Integer numQuestions, Boolean completed, Double overallScore, 
                                    Date createdAt, List<QuestionFeedbackDto> questions) {
        this.id = id;
        this.techStack = techStack;
        this.experienceLevel = experienceLevel;
        this.numQuestions = numQuestions;
        this.completed = completed;
        this.overallScore = overallScore;
        this.createdAt = createdAt;
        this.questions = questions;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public Integer getNumQuestions() { return numQuestions; }
    public void setNumQuestions(Integer numQuestions) { this.numQuestions = numQuestions; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public Double getOverallScore() { return overallScore; }
    public void setOverallScore(Double overallScore) { this.overallScore = overallScore; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public List<QuestionFeedbackDto> getQuestions() { return questions; }
    public void setQuestions(List<QuestionFeedbackDto> questions) { this.questions = questions; }
}
