import React, { useState, useEffect } from 'react';
import { interviewService } from '../services/api';
import { Award, BarChart2, BookOpen, Clock, ChevronRight, Sparkles, Loader2, RefreshCw } from 'lucide-react';

export default function DashboardView({ onViewChange, onSelectInterview }) {
  const [stats, setStats] = useState(null);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchDashboardData = async () => {
    setLoading(true);
    setError('');
    try {
      const statsData = await interviewService.getDashboardStats();
      const historyData = await interviewService.getHistory();
      setStats(statsData);
      setHistory(historyData);
    } catch (err) {
      console.error(err);
      setError('Failed to fetch dashboard data. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  const getScoreColor = (score) => {
    if (score >= 80) return 'var(--color-success)';
    if (score >= 60) return 'var(--color-warning)';
    return 'var(--color-danger)';
  };

  if (loading) {
    return (
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '50vh', gap: '15px' }}>
        <Loader2 className="animate-spin" size={40} color="var(--accent-primary)" />
        <p style={{ color: 'var(--text-secondary)', fontFamily: 'var(--font-primary)' }}>Loading your dashboard...</p>
      </div>
    );
  }

  return (
    <div className="animate-fade-in">
      <div className="dashboard-title-row">
        <div>
          <h1 className="dashboard-title">
            Your <span className="text-gradient">Journey</span>
          </h1>
          <p className="dashboard-subtitle">Track your growth and practice history</p>
        </div>
        <button 
          id="dashboard-refresh"
          onClick={fetchDashboardData}
          className="btn btn-secondary" 
          style={{ padding: '12px', borderRadius: '50%' }}
          title="Refresh Data"
        >
          <RefreshCw size={16} />
        </button>
      </div>

      {error && (
        <div className="alert-message alert-danger">
          {error}
        </div>
      )}

      {/* Metrics Row */}
      {stats && (
        <div className="stats-grid">
          <div className="glass-panel stat-card">
            <div className="stat-icon-container">
              <BookOpen size={24} />
            </div>
            <div>
              <p className="stat-lbl">Total Practices</p>
              <h3 className="stat-val">{stats.totalInterviews}</h3>
            </div>
          </div>

          <div className="glass-panel stat-card">
            <div className="stat-icon-container">
              <BarChart2 size={24} />
            </div>
            <div>
              <p className="stat-lbl">Completion Rate</p>
              <h3 className="stat-val">
                {stats.totalInterviews > 0 
                  ? Math.round((stats.completedInterviews / stats.totalInterviews) * 100) 
                  : 0}%
              </h3>
            </div>
          </div>

          <div className="glass-panel stat-card">
            <div className="stat-icon-container">
              <Award size={24} />
            </div>
            <div>
              <p className="stat-lbl">Average Score</p>
              <h3 className="stat-val" style={{ color: getScoreColor(stats.averageScore) }}>
                {stats.averageScore > 0 ? `${stats.averageScore}%` : 'N/A'}
              </h3>
            </div>
          </div>
        </div>
      )}

      {/* History section */}
      <div className="glass-panel dashboard-history-card">
        <h2 className="history-card-title">
          <Clock size={22} className="clock-icon" />
          Interview History
        </h2>

        {history.length === 0 ? (
          <div className="empty-state-box">
            <div className="empty-state-icon-wrapper">
              <BookOpen size={36} />
            </div>
            <div>
              <h3 className="empty-state-title">No Sessions Yet</h3>
              <p className="empty-state-desc">
                Start your first mock interview to begin growing your skills. Every practice session brings you closer to confidence!
              </p>
            </div>
            <button 
              id="start-first-practice"
              onClick={() => onViewChange('config')}
              className="btn btn-primary"
              style={{ marginTop: '10px' }}
            >
              <Sparkles size={16} />
              Begin First Practice
            </button>
          </div>
        ) : (
          <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
            {history.map((session) => (
              <div 
                key={session.id}
                onClick={() => onSelectInterview(session.id)}
                className="history-card-row"
              >
                <div className="history-item-meta">
                  <div className="history-session-num">
                    <span className="num-lbl">SESSION</span>
                    <span className="num-val">#{session.id}</span>
                  </div>

                  <div>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                      <span className="history-tech-name">
                        {session.techStack}
                      </span>
                      <span className="history-level-badge">
                        {session.experienceLevel}
                      </span>
                    </div>
                    <p className="history-session-date">
                      {formatDate(session.createdAt)} • {session.numQuestions} Questions
                    </p>
                  </div>
                </div>

                <div className="history-status-container">
                  {session.completed ? (
                    <div className="history-score-col">
                      <span className="history-score-lbl">Score</span>
                      <span className="history-score-val" style={{ color: getScoreColor(session.overallScore) }}>
                        {Math.round(session.overallScore)}%
                      </span>
                    </div>
                  ) : (
                    <span className="history-progress-badge">
                      In Progress
                    </span>
                  )}
                  <ChevronRight size={20} className="history-chevron" />
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
