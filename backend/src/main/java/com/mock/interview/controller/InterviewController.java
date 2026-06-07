package com.mock.interview.controller;

import com.mock.interview.dto.*;
import com.mock.interview.entity.User;
import com.mock.interview.repository.UserRepository;
import com.mock.interview.security.UserDetailsImpl;
import com.mock.interview.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));
    }

    @PostMapping("/config")
    public ResponseEntity<List<QuestionDto>> createInterview(@Valid @RequestBody InterviewConfigRequest configRequest) {
        User user = getCurrentUser();
        List<QuestionDto> questions = interviewService.createSession(user, configRequest);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/submit")
    public ResponseEntity<FeedbackResponse> submitAnswer(@Valid @RequestBody AnswerSubmitRequest submitRequest) {
        User user = getCurrentUser();
        FeedbackResponse feedback = interviewService.submitAnswer(user, submitRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        User user = getCurrentUser();
        DashboardStats stats = interviewService.getDashboardStats(user);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/history")
    public ResponseEntity<List<InterviewDetailsResponse>> getHistory() {
        User user = getCurrentUser();
        List<InterviewDetailsResponse> history = interviewService.getAllSessions(user);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewDetailsResponse> getInterviewDetails(@PathVariable Long id) {
        User user = getCurrentUser();
        InterviewDetailsResponse details = interviewService.getSessionDetails(id, user);
        return ResponseEntity.ok(details);
    }
}
