package com.mock.interview.dto;

public class QuestionFeedbackDto {
    private Long questionId;
    private String questionText;
    private Integer questionOrder;
    private String userAnswer;
    private Double score;
    private String strengths;
    private String improvements;
    private String idealAnswer;

    public QuestionFeedbackDto(Long questionId, String questionText, Integer questionOrder, 
                               String userAnswer, Double score, String strengths, 
                               String improvements, String idealAnswer) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionOrder = questionOrder;
        this.userAnswer = userAnswer;
        this.score = score;
        this.strengths = strengths;
        this.improvements = improvements;
        this.idealAnswer = idealAnswer;
    }

    // Getters and Setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public Integer getQuestionOrder() { return questionOrder; }
    public void setQuestionOrder(Integer questionOrder) { this.questionOrder = questionOrder; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }

    public String getImprovements() { return improvements; }
    public void setImprovements(String improvements) { this.improvements = improvements; }

    public String getIdealAnswer() { return idealAnswer; }
    public void setIdealAnswer(String idealAnswer) { this.idealAnswer = idealAnswer; }
}
