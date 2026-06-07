import React, { useState } from 'react';
import { interviewService } from '../services/api';
import { Loader2, ArrowLeft, ArrowRight, HelpCircle, Lightbulb } from 'lucide-react';

export default function SessionView({ sessionId, questions, techStack, experienceLevel, onFinish }) {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [answers, setAnswers] = useState({}); // Stores answers locally: { questionId: answerText }
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  const currentQuestion = questions[currentIndex];
  const progressPercent = Math.round(((currentIndex + 1) / questions.length) * 100);

  const handleTextChange = (e) => {
    setAnswers({
      ...answers,
      [currentQuestion.id]: e.target.value
    });
  };

  const handleNavigate = (direction) => {
    setError('');
    if (direction === 'next' && currentIndex < questions.length - 1) {
      setCurrentIndex(currentIndex + 1);
    } else if (direction === 'prev' && currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
    }
  };

  const handleSubmitQuestion = async () => {
    setError('');
    setSubmitting(true);
    const currentAnswer = answers[currentQuestion.id] || '';

    try {
      // Submit the answer for the current question to the database
      await interviewService.submitAnswer(currentQuestion.id, currentAnswer);

      // If it's the last question, we finish and navigate to feedback
      if (currentIndex === questions.length - 1) {
        onFinish(sessionId);
      } else {
        // Otherwise, move to the next question
        setCurrentIndex(currentIndex + 1);
      }
    } catch (err) {
      console.error(err);
      setError('Failed to submit your response. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="session-card animate-fade-in">
      {/* Header Info */}
      <div className="session-meta-row">
        <div>
          <span className="session-stack-badge">
            {experienceLevel}
          </span>
          <span className="session-stack-title">
            {techStack} Mock Session
          </span>
        </div>

        <div className="session-progress-counter">
          Question <span style={{ color: 'var(--text-primary)', fontWeight: 700 }}>{currentIndex + 1}</span> of {questions.length}
        </div>
      </div>

      {/* Progress Bar */}
      <div className="session-progress-bar-track">
        <div 
          className="session-progress-bar-fill"
          style={{ width: `${progressPercent}%` }}
        ></div>
      </div>

      {error && (
        <div className="alert-message alert-danger">
          {error}
        </div>
      )}

      {/* Main Question Card */}
      <div className="glass-panel question-panel">
        <div className="question-prompt-row">
          <HelpCircle size={24} className="question-prompt-icon" />
          <h2 className="question-prompt-text">
            {currentQuestion.questionText}
          </h2>
        </div>

        {/* Answer text area */}
        <div className="response-textarea-group">
          <div className="response-textarea-header">
            <label className="form-label">Your Response</label>
            <span className="character-badge">
              {(answers[currentQuestion.id] || '').length} characters
            </span>
          </div>
          <textarea
            id="session-answer-textarea"
            className="response-textarea"
            placeholder="Type your detailed response here. Be thorough and use specific technical terms to score well."
            value={answers[currentQuestion.id] || ''}
            onChange={handleTextChange}
            disabled={submitting}
          ></textarea>
        </div>
      </div>

      {/* Info Tip Banner */}
      <div className="tips-banner">
        <Lightbulb size={20} className="tips-banner-icon" />
        <p className="tips-banner-text">
          <strong>Pro-tip:</strong> Mention specific keywords, class libraries, framework functions, or architectural trade-offs. The evaluator analyzes technical depth as well as overall conceptual accuracy.
        </p>
      </div>

      {/* Navigation & Submission Controls */}
      <div className="session-navigation-row">
        <button
          id="session-prev"
          type="button"
          onClick={() => handleNavigate('prev')}
          className="btn btn-secondary"
          disabled={currentIndex === 0 || submitting}
        >
          <ArrowLeft size={16} />
          Back
        </button>

        <button
          id="session-submit-next"
          type="button"
          onClick={handleSubmitQuestion}
          className="btn btn-primary"
          disabled={submitting}
          style={{ minWidth: '180px' }}
        >
          {submitting ? (
            <>
              <Loader2 className="animate-spin" size={18} />
              Evaluating...
            </>
          ) : (
            <>
              {currentIndex === questions.length - 1 ? 'Submit & Finish' : 'Save & Next'}
              <ArrowRight size={16} />
            </>
          )}
        </button>
      </div>
    </div>
  );
}
