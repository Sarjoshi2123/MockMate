package com.mock.interview.service;

import com.mock.interview.dto.*;
import com.mock.interview.entity.AnswerFeedback;
import com.mock.interview.entity.InterviewSession;
import com.mock.interview.entity.Question;
import com.mock.interview.entity.User;
import com.mock.interview.repository.AnswerFeedbackRepository;
import com.mock.interview.repository.InterviewSessionRepository;
import com.mock.interview.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    @Autowired
    private InterviewSessionRepository sessionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerFeedbackRepository feedbackRepository;

    @Autowired
    private AiService aiService;

    @Transactional
    public List<QuestionDto> createSession(User user, InterviewConfigRequest config) {
        // 1. Create session instance
        InterviewSession session = new InterviewSession(
                user,
                config.getTechStack(),
                config.getExperienceLevel(),
                config.getNumQuestions()
        );

        // 2. Generate questions from AI service
        List<String> questionTexts = aiService.generateQuestions(
                config.getTechStack(),
                config.getExperienceLevel(),
                config.getNumQuestions()
        );

        // 3. Save questions attached to the session
        for (int i = 0; i < questionTexts.size(); i++) {
            Question question = new Question(questionTexts.get(i), i + 1);
            session.addQuestion(question);
        }

        InterviewSession savedSession = sessionRepository.save(session);

        // 4. Return DTOs to UI
        return savedSession.getQuestions().stream()
                .map(q -> new QuestionDto(q.getId(), q.getQuestionText(), q.getQuestionOrder()))
                .collect(Collectors.toList());
    }

    @Transactional
    public FeedbackResponse submitAnswer(User user, AnswerSubmitRequest request) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + request.getQuestionId()));

        InterviewSession session = question.getInterviewSession();
        if (!session.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access to this interview session.");
        }

        // Check if feedback already exists, delete if overwriting (or update)
        if (question.getAnswerFeedback() != null) {
            feedbackRepository.delete(question.getAnswerFeedback());
            question.setAnswerFeedback(null);
        }

        // Call AI evaluation
        Map<String, Object> evaluation = aiService.evaluateAnswer(
                question.getQuestionText(),
                request.getUserAnswer(),
                session.getTechStack(),
                session.getExperienceLevel()
        );

        Double score = (Double) evaluation.get("score");
        String strengths = (String) evaluation.get("strengths");
        String improvements = (String) evaluation.get("improvements");
        String ideal = (String) evaluation.get("ideal");

        // Save new feedback
        AnswerFeedback feedback = new AnswerFeedback(
                request.getUserAnswer(),
                score,
                strengths,
                improvements,
                ideal
        );
        question.setAnswerFeedback(feedback);
        feedbackRepository.save(feedback);

        // Check if all questions are answered
        long answeredCount = session.getQuestions().stream()
                .filter(q -> q.getAnswerFeedback() != null)
                .count();

        if (answeredCount == session.getNumQuestions()) {
            session.setCompleted(true);
            double totalScore = session.getQuestions().stream()
                    .mapToDouble(q -> q.getAnswerFeedback().getScore())
                    .sum();
            session.setOverallScore(totalScore / session.getNumQuestions());
            sessionRepository.save(session);
        }

        return new FeedbackResponse(
                question.getId(),
                score,
                strengths,
                improvements,
                ideal
        );
    }

    @Transactional(readOnly = true)
    public DashboardStats getDashboardStats(User user) {
        List<InterviewSession> sessions = sessionRepository.findByUserOrderByCreatedAtDesc(user);

        long total = sessions.size();
        long completed = sessions.stream().filter(InterviewSession::getCompleted).count();
        
        double avgScore = 0.0;
        List<InterviewSession> completedSessions = sessions.stream()
                .filter(s -> s.getCompleted() && s.getOverallScore() != null)
                .collect(Collectors.toList());
                
        if (!completedSessions.isEmpty()) {
            double sum = completedSessions.stream()
                    .mapToDouble(InterviewSession::getOverallScore)
                    .sum();
            avgScore = sum / completedSessions.size();
        }

        // Round average score to one decimal place
        avgScore = Math.round(avgScore * 10.0) / 10.0;

        Map<String, Long> stackCounts = sessions.stream()
                .collect(Collectors.groupingBy(InterviewSession::getTechStack, Collectors.counting()));

        return new DashboardStats(total, avgScore, completed, stackCounts);
    }

    @Transactional(readOnly = true)
    public List<InterviewDetailsResponse> getAllSessions(User user) {
        List<InterviewSession> sessions = sessionRepository.findByUserOrderByCreatedAtDesc(user);
        return sessions.stream()
                .map(this::mapToDetailsResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InterviewDetailsResponse getSessionDetails(Long sessionId, User user) {
        InterviewSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + sessionId));

        if (!session.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized access to this interview session.");
        }

        return mapToDetailsResponse(session);
    }

    private InterviewDetailsResponse mapToDetailsResponse(InterviewSession session) {
        List<QuestionFeedbackDto> questions = session.getQuestions().stream()
                .map(q -> {
                    AnswerFeedback f = q.getAnswerFeedback();
                    return new QuestionFeedbackDto(
                            q.getId(),
                            q.getQuestionText(),
                            q.getQuestionOrder(),
                            f != null ? f.getUserAnswer() : null,
                            f != null ? f.getScore() : null,
                            f != null ? f.getStrengths() : null,
                            f != null ? f.getImprovements() : null,
                            f != null ? f.getIdealAnswer() : null
                    );
                })
                .sorted(Comparator.comparing(QuestionFeedbackDto::getQuestionOrder))
                .collect(Collectors.toList());

        return new InterviewDetailsResponse(
                session.getId(),
                session.getTechStack(),
                session.getExperienceLevel(),
                session.getNumQuestions(),
                session.getCompleted(),
                session.getOverallScore(),
                session.getCreatedAt(),
                questions
        );
    }
}
