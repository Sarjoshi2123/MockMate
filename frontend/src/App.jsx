import React, { useState, useEffect } from 'react';
import { authService, interviewService } from './services/api';
import Navbar from './components/Navbar';
import AuthView from './components/AuthView';
import DashboardView from './components/DashboardView';
import ConfigView from './components/ConfigView';
import SessionView from './components/SessionView';
import FeedbackView from './components/FeedbackView';

export default function App() {
  const [user, setUser] = useState(authService.getCurrentUser());
  const [view, setView] = useState(user ? 'dashboard' : 'auth');
  const [currentSessionId, setCurrentSessionId] = useState(null);
  const [currentQuestions, setCurrentQuestions] = useState([]);
  const [techStack, setTechStack] = useState('');
  const [experienceLevel, setExperienceLevel] = useState('');

  // Handle successful login/signup
  const handleAuthSuccess = (userData) => {
    setUser(userData);
    setView('dashboard');
  };

  // Sync state if user logs out in another tab or token gets invalid
  useEffect(() => {
    const handleStorageChange = () => {
      const currentUser = authService.getCurrentUser();
      setUser(currentUser);
      if (!currentUser) {
        setView('auth');
      }
    };
    window.addEventListener('storage', handleStorageChange);
    return () => window.removeEventListener('storage', handleStorageChange);
  }, []);

  const handleViewChange = (newView) => {
    setView(newView);
  };

  const handleStartSession = (sessionId, questions, stack, level) => {
    setCurrentSessionId(sessionId);
    setCurrentQuestions(questions);
    setTechStack(stack);
    setExperienceLevel(level);
    setView('session');
  };

  const handleFinishSession = (sessionId) => {
    setCurrentSessionId(sessionId);
    setView('feedback');
  };

  const handleSelectInterview = async (sessionId) => {
    try {
      const details = await interviewService.getDetails(sessionId);
      setCurrentSessionId(sessionId);
      
      if (details.completed) {
        setView('feedback');
      } else {
        // Resume an in-progress session
        const questionsList = details.questions.map(q => ({
          id: q.questionId,
          questionText: q.questionText,
          questionOrder: q.questionOrder
        }));
        
        setCurrentQuestions(questionsList);
        setTechStack(details.techStack);
        setExperienceLevel(details.experienceLevel);
        setView('session');
      }
    } catch (err) {
      console.error('Failed to load session details', err);
    }
  };

  return (
    <div className="app-layout">
      {/* Premium Ambient Background Glowing Orbs */}
      <div className="ambient-glow-wrapper">
        <div className="ambient-orb orb-1"></div>
        <div className="ambient-orb orb-2"></div>
      </div>

      {user && (
        <Navbar 
          user={user} 
          currentView={view} 
          onViewChange={handleViewChange} 
        />
      )}
      
      <main className="main-content">
        {!user && <AuthView onAuthSuccess={handleAuthSuccess} />}
        
        {user && view === 'dashboard' && (
          <DashboardView 
            onViewChange={handleViewChange} 
            onSelectInterview={handleSelectInterview} 
          />
        )}
        
        {user && view === 'config' && (
          <ConfigView 
            onStartSession={handleStartSession} 
            onCancel={() => setView('dashboard')} 
          />
        )}
        
        {user && view === 'session' && (
          <SessionView 
            sessionId={currentSessionId}
            questions={currentQuestions}
            techStack={techStack}
            experienceLevel={experienceLevel}
            onFinish={handleFinishSession}
          />
        )}
        
        {user && view === 'feedback' && (
          <FeedbackView 
            sessionId={currentSessionId} 
            onBackToDashboard={() => setView('dashboard')} 
          />
        )}
      </main>
    </div>
  );
}
