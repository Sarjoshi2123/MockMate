package com.mock.interview.dto;

import jakarta.validation.constraints.NotNull;

public class AnswerSubmitRequest {
    @NotNull
    private Long questionId;

    private String userAnswer;

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
}
