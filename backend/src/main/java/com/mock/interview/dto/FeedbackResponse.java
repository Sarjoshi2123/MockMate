package com.mock.interview.dto;

public class FeedbackResponse {
    private Long questionId;
    private Double score;
    private String strengths;
    private String improvements;
    private String idealAnswer;

    public FeedbackResponse(Long questionId, Double score, String strengths, String improvements, String idealAnswer) {
        this.questionId = questionId;
        this.score = score;
        this.strengths = strengths;
        this.improvements = improvements;
        this.idealAnswer = idealAnswer;
    }

    // Getters and Setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getImprovements() { return improvements; }
    public void setImprovements(String improvements) { this.improvements = improvements; }

    public String getIdealAnswer() { return idealAnswer; }
    public void setIdealAnswer(String idealAnswer) { this.idealAnswer = idealAnswer; }
}
