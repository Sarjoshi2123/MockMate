import React, { useState } from 'react';
import { interviewService } from '../services/api';
import { Sparkles, Terminal, Code, Cpu, Braces, ArrowRight, Loader2, Award } from 'lucide-react';

const STACKS = [
  {
    name: 'Java/Spring Boot',
    desc: 'Core Java, Spring, Hibernate, JMM, garbage collection, and DB tuning.',
    icon: Terminal,
    emoji: '☕'
  },
  {
    name: 'React.js',
    desc: 'Virtual DOM, reconciliation, hooks, State/Context, optimization, and concurrent.',
    icon: Code,
    emoji: '⚛️'
  },
  {
    name: 'System Design',
    desc: 'Scalability, microservices, load balancing, sharding, caching, queues, and CAP.',
    icon: Cpu,
    emoji: '🏗️'
  },
  {
    name: 'Python',
    desc: 'Core Python, decorators, generators, GIL, data structures, and frameworks.',
    icon: Braces,
    emoji: '🐍'
  }
];

const LEVELS = ['Junior', 'Mid', 'Senior'];
const LEVEL_EMOJIS = { Junior: '○', Mid: '◐', Senior: '●' };

export default function ConfigView({ onStartSession, onCancel }) {
  const [techStack, setTechStack] = useState(STACKS[0].name);
  const [experienceLevel, setExperienceLevel] = useState('Mid');
  const [numQuestions, setNumQuestions] = useState(3);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleGenerate = async () => {
    setLoading(true);
    setError('');
    try {
      const questions = await interviewService.createInterview(techStack, experienceLevel, numQuestions);
      
      // Fetch the latest session from history to get its ID
      const history = await interviewService.getHistory();
      const latestSession = history[0];
      
      onStartSession(latestSession.id, questions, techStack, experienceLevel);
    } catch (err) {
      console.error(err);
      setError('Failed to generate interview questions. Please check your network and try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="glass-panel config-card animate-fade-in">
      <div className="config-header">
        <h1 className="config-title">
          Configure Your <span className="text-gradient">Mock Interview</span>
        </h1>
        <p className="config-subtitle">
          Choose your tech stack and depth. Our AI will curate custom, scenario-based questions.
        </p>
      </div>

      {error && (
        <div className="alert-message alert-danger">
          {error}
        </div>
      )}

      {/* Tech Stack Selector */}
      <div className="form-group" style={{ marginBottom: '32px' }}>
        <label className="form-label">
          1. Select Technology Stack
        </label>
        <div className="stack-grid">
          {STACKS.map((stack) => {
            const Icon = stack.icon;
            const isSelected = techStack === stack.name;
            return (
              <div 
                key={stack.name}
                id={`stack-${stack.name.replace(/[^a-zA-Z]/g, '-').toLowerCase()}`}
                onClick={() => setTechStack(stack.name)}
                className={`stack-card ${isSelected ? 'selected' : ''}`}
              >
                <div className="stack-icon-wrapper">
                  <Icon size={20} />
                </div>
                <h3 className="stack-name">{stack.name}</h3>
                <p className="stack-desc">{stack.desc}</p>
              </div>
            );
          })}
        </div>
      </div>

      {/* Experience Level Selector */}
      <div className="form-group" style={{ marginBottom: '32px' }}>
        <label className="form-label">
          2. Target Experience Level
        </label>
        <div className="level-toggle-group">
          {LEVELS.map((level) => {
            const isSelected = experienceLevel === level;
            return (
              <button
                key={level}
                id={`level-${level.toLowerCase()}`}
                type="button"
                onClick={() => setExperienceLevel(level)}
                className={`level-btn ${isSelected ? 'selected' : ''}`}
              >
                <span>{LEVEL_EMOJIS[level]}</span>
                {level} Level
              </button>
            );
          })}
        </div>
      </div>

      {/* Number of Questions Slider */}
      <div className="slider-container" style={{ marginBottom: '32px' }}>
        <div className="slider-header">
          <label className="form-label">
            3. Number of Questions
          </label>
          <span className="slider-badge">
            {numQuestions} Questions
          </span>
        </div>
        <div className="slider-track-row">
          <span className="slider-limit-label">1</span>
          <input
            id="question-count-slider"
            type="range"
            min="1"
            max="10"
            value={numQuestions}
            onChange={(e) => setNumQuestions(parseInt(e.target.value))}
            className="premium-slider"
          />
          <span className="slider-limit-label">10</span>
        </div>
      </div>

      {/* Action Buttons */}
      <div className="config-footer">
        <button 
          id="config-cancel"
          onClick={onCancel}
          className="btn btn-secondary"
          disabled={loading}
        >
          Cancel
        </button>
        <button 
          id="config-generate"
          onClick={handleGenerate}
          className="btn btn-primary"
          disabled={loading}
          style={{ minWidth: '180px' }}
        >
          {loading ? (
            <>
              <Loader2 className="animate-spin" size={18} />
              Generating...
            </>
          ) : (
            <>
              Generate Questions
              <ArrowRight size={16} />
            </>
          )}
        </button>
      </div>
    </div>
  );
}
