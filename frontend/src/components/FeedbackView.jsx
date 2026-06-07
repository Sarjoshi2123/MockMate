import React, { useState, useEffect } from 'react';
import { interviewService } from '../services/api';
import { Award, ChevronDown, ChevronUp, CheckCircle, AlertTriangle, BookOpen, ArrowLeft, Loader2 } from 'lucide-react';

export default function FeedbackView({ sessionId, onBackToDashboard }) {
  const [details, setDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [expandedQuestions, setExpandedQuestions] = useState({});

  useEffect(() => {
    const fetchDetails = async () => {
      setLoading(true);
      setError('');
      try {
        const data = await interviewService.getDetails(sessionId);
        setDetails(data);
        // Expand the first question by default
        if (data.questions && data.questions.length > 0) {
          setExpandedQuestions({ [data.questions[0].questionId]: true });
        }
      } catch (err) {
        console.error(err);
        setError('Failed to load feedback details. Please return to the dashboard.');
      } finally {
        setLoading(false);
      }
    };

    fetchDetails();
  }, [sessionId]);

  const toggleExpand = (qId) => {
    setExpandedQuestions(prev => ({
      ...prev,
      [qId]: !prev[qId]
    }));
  };

  const getScoreColor = (score) => {
    if (score >= 80) return 'var(--color-success)';
    if (score >= 60) return 'var(--color-warning)';
    return 'var(--color-danger)';
  };

  const getOverallGrade = (score) => {
    if (score >= 90) return { title: 'Expert — L3', desc: 'Excellent response structure and deep technical mastery.' };
    if (score >= 75) return { title: 'Strong Pass — L2', desc: 'Good clarity, matches key vocabulary, knows the core mechanics well.' };
    if (score >= 50) return { title: 'Borderline Pass — L1', desc: 'Demonstrated initial understanding but missed technical edge cases.' };
    return { title: 'Needs Practice — L0', desc: 'Answer is too brief or contains fundamental misunderstandings. Keep practicing!' };
  };

  if (loading) {
    return (
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '50vh', gap: '15px' }}>
        <Loader2 className="animate-spin" size={40} color="var(--accent-primary)" />
        <p style={{ color: 'var(--text-secondary)', fontFamily: 'var(--font-primary)' }}>Preparing your performance report...</p>
      </div>
    );
  }

  if (error || !details) {
    return (
      <div className="glass-panel empty-state-box">
        <AlertTriangle size={48} color="var(--color-danger)" style={{ marginBottom: '8px' }} />
        <h3 className="empty-state-title">Something went wrong</h3>
        <p className="empty-state-desc" style={{ marginBottom: '20px' }}>{error || 'No data was returned.'}</p>
        <button id="feedback-back-error" onClick={onBackToDashboard} className="btn btn-secondary">
          <ArrowLeft size={16} /> Return to Dashboard
        </button>
      </div>
    );
  }

  const grade = getOverallGrade(details.overallScore || 0);

  return (
    <div className="feedback-card animate-fade-in">
      {/* Return button */}
      <button 
        id="feedback-back"
        onClick={onBackToDashboard}
        className="btn btn-secondary feedback-back-btn"
      >
        <ArrowLeft size={16} />
        Back to Dashboard
      </button>

      {/* Hero Feedback Header */}
      <div className="glass-panel report-hero-banner">
        <div className="report-hero-content">
          <div className="report-hero-info">
            <div className="report-meta-pills">
              <span className="report-level-pill">
                {details.experienceLevel} Level
              </span>
              <span className="report-tech-pill">
                {details.techStack}
              </span>
            </div>
            
            <h1 className="report-title">
              Performance <span className="text-gradient">Report</span>
            </h1>
            <h3 className="report-grade-title">
              {grade.title}
            </h3>
            <p className="report-grade-desc">
              {grade.desc}
            </p>
          </div>

          <div className="report-score-box">
            <Award size={36} color={getScoreColor(details.overallScore || 0)} style={{ marginBottom: '8px' }} />
            <span className="report-score-lbl">Overall Score</span>
            <span className="report-score-num" style={{ color: getScoreColor(details.overallScore || 0) }}>
              {details.overallScore != null ? `${Math.round(details.overallScore)}%` : 'N/A'}
            </span>
          </div>
        </div>
      </div>

      {/* Questions Breakdown List */}
      <h2 className="report-breakdown-title">
        Detailed Question Breakdown
      </h2>

      <div className="accordion-list">
        {details.questions.map((q, idx) => {
          const isExpanded = !!expandedQuestions[q.questionId];
          const hasFeedback = q.score != null;
          return (
            <div 
              key={q.questionId}
              className="accordion-item glass-panel"
            >
              {/* Question header bar */}
              <div 
                onClick={() => toggleExpand(q.questionId)}
                className="accordion-header"
              >
                <div className="accordion-header-left">
                  <div className="accordion-index-badge">
                    {idx + 1}
                  </div>
                  <h3 className="accordion-question-text">
                    {q.questionText}
                  </h3>
                </div>

                <div className="accordion-header-right">
                  {hasFeedback ? (
                    <span 
                      className="accordion-score-badge"
                      style={{ color: getScoreColor(q.score) }}
                    >
                      {Math.round(q.score)}%
                    </span>
                  ) : (
                    <span style={{ fontSize: '0.82rem', color: 'var(--text-muted)' }}>Not answered</span>
                  )}
                  {isExpanded ? <ChevronUp size={20} color="var(--text-muted)" /> : <ChevronDown size={20} color="var(--text-muted)" />}
                </div>
              </div>

              {/* Question Feedback details body */}
              {isExpanded && (
                <div className="accordion-body animate-slide-up">
                  
                  {/* User's Answer */}
                  <div>
                    <h4 className="feedback-section-title">
                      Your Answer
                    </h4>
                    <p className={`user-answer-box ${q.userAnswer ? '' : 'empty'}`}>
                      {q.userAnswer || 'No answer was provided.'}
                    </p>
                  </div>

                  {hasFeedback && (
                    <div className="feedback-cards-grid">
                      {/* Strengths */}
                      <div className="eval-card eval-card-success">
                        <h4 className="eval-card-header success">
                          <CheckCircle size={16} /> Key Strengths
                        </h4>
                        <p className="eval-card-body">
                          {q.strengths || 'None'}
                        </p>
                      </div>

                      {/* Suggestions for improvement */}
                      <div className="eval-card eval-card-warning">
                        <h4 className="eval-card-header warning">
                          <AlertTriangle size={16} /> Improvements Needed
                        </h4>
                        <p className="eval-card-body">
                          {q.improvements || 'None'}
                        </p>
                      </div>
                    </div>
                  )}

                  {/* Ideal Reference Answer */}
                  {hasFeedback && q.idealAnswer && (
                    <div className="reference-answer-container">
                      <h4 className="reference-answer-header">
                        <BookOpen size={16} /> Reference Answer
                      </h4>
                      <p className="reference-answer-body">
                        {q.idealAnswer}
                      </p>
                    </div>
                  )}

                </div>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}
