package com.mock.interview.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InterviewConfigRequest {
    @NotBlank
    private String techStack;

    @NotBlank
    private String experienceLevel;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer numQuestions;

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public Integer getNumQuestions() { return numQuestions; }
    public void setNumQuestions(Integer numQuestions) { this.numQuestions = numQuestions; }
}
