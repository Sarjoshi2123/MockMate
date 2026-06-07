package com.mock.interview.repository;

import com.mock.interview.entity.AnswerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerFeedbackRepository extends JpaRepository<AnswerFeedback, Long> {
}
