import React from 'react';
import { authService } from '../services/api';
import { LogOut, User, Sparkles, History } from 'lucide-react';

export default function Navbar({ user, onViewChange, currentView }) {
  return (
    <nav className="floating-navbar">
      <div 
        onClick={() => onViewChange('dashboard')}
        className="nav-brand"
      >
        <div className="nav-brand-logo">
          <Sparkles size={20} color="#0A0A0A" />
        </div>
        <span className="nav-brand-text">
          Mock<span style={{ color: 'var(--accent-primary)' }}>Mate</span>
        </span>
      </div>

      {user && (
        <div className="nav-actions">
          <button 
            onClick={() => onViewChange('dashboard')}
            className={`nav-link-btn ${currentView === 'dashboard' ? 'active' : ''}`}
          >
            <History size={16} />
            Dashboard
          </button>
          
          <button 
            onClick={() => onViewChange('config')}
            className="btn btn-primary"
          >
            <Sparkles size={16} />
            Start Interview
          </button>

          <div className="nav-user-chip">
            <User size={16} className="user-icon" />
            <span className="nav-user-name">
              {user.username}
            </span>
          </div>

          <button 
            onClick={authService.logout}
            className="nav-logout-btn"
            title="Logout"
          >
            <LogOut size={16} />
          </button>
        </div>
      )}
    </nav>
  );
}
