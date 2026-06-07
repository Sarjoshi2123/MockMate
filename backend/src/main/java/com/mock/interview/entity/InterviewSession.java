package com.mock.interview.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "interview_sessions")
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tech_stack", nullable = false)
    private String techStack;

    @Column(name = "experience_level", nullable = false)
    private String experienceLevel;

    @Column(name = "num_questions", nullable = false)
    private Integer numQuestions;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "overall_score")
    private Double overallScore;

    @OneToMany(mappedBy = "interviewSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public InterviewSession() {
        this.createdAt = new Date();
    }

    public InterviewSession(User user, String techStack, String experienceLevel, Integer numQuestions) {
        this.user = user;
        this.techStack = techStack;
        this.experienceLevel = experienceLevel;
        this.numQuestions = numQuestions;
        this.createdAt = new Date();
        this.completed = false;
    }

    // Helpers to manage bi-directional relationship
    public void addQuestion(Question question) {
        questions.add(question);
        question.setInterviewSession(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public Integer getNumQuestions() { return numQuestions; }
    public void setNumQuestions(Integer numQuestions) { this.numQuestions = numQuestions; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public Double getOverallScore() { return overallScore; }
    public void setOverallScore(Double overallScore) { this.overallScore = overallScore; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
