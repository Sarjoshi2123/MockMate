package com.mock.interview.dto;

public class QuestionDto {
    private Long id;
    private String questionText;
    private Integer questionOrder;

    public QuestionDto(Long id, String questionText, Integer questionOrder) {
        this.id = id;
        this.questionText = questionText;
        this.questionOrder = questionOrder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public Integer getQuestionOrder() { return questionOrder; }
    public void setQuestionOrder(Integer questionOrder) { this.questionOrder = questionOrder; }
}
