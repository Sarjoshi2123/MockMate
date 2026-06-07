package com.mock.interview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private InterviewSession interviewSession;

    @Column(name = "question_text", nullable = false, length = 2000)
    private String questionText;

    @Column(name = "question_order", nullable = false)
    private Integer questionOrder;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private AnswerFeedback answerFeedback;

    public Question() {}

    public Question(String questionText, Integer questionOrder) {
        this.questionText = questionText;
        this.questionOrder = questionOrder;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public InterviewSession getInterviewSession() { return interviewSession; }
    public void setInterviewSession(InterviewSession interviewSession) { this.interviewSession = interviewSession; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public Integer getQuestionOrder() { return questionOrder; }
    public void setQuestionOrder(Integer questionOrder) { this.questionOrder = questionOrder; }

    public AnswerFeedback getAnswerFeedback() { return answerFeedback; }
    public void setAnswerFeedback(AnswerFeedback answerFeedback) { 
        this.answerFeedback = answerFeedback;
        if (answerFeedback != null && answerFeedback.getQuestion() != this) {
            answerFeedback.setQuestion(this);
        }
    }
}
