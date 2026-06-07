package com.mock.interview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "answer_feedbacks")
public class AnswerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "user_answer", length = 5000)
    private String userAnswer;

    @Column(nullable = false)
    private Double score;

    @Column(length = 2000)
    private String strengths;

    @Column(length = 2000)
    private String improvements;

    @Column(name = "ideal_answer", length = 2000)
    private String idealAnswer;

    public AnswerFeedback() {}

    public AnswerFeedback(String userAnswer, Double score, String strengths, String improvements, String idealAnswer) {
        this.userAnswer = userAnswer;
        this.score = score;
        this.strengths = strengths;
        this.improvements = improvements;
        this.idealAnswer = idealAnswer;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

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
