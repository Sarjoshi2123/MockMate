import React, { useState } from 'react';
import { authService } from '../services/api';
import { User, Mail, Lock, ShieldCheck, ArrowRight, Loader2 } from 'lucide-react';

export default function AuthView({ onAuthSuccess }) {
  const [isLogin, setIsLogin] = useState(true);
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setMessage('');
    setLoading(true);

    try {
      if (isLogin) {
        const userData = await authService.login(username, password);
        onAuthSuccess(userData);
      } else {
        await authService.signup(username, email, password);
        setMessage('Registration successful! Please sign in.');
        setIsLogin(true);
        setPassword('');
      }
    } catch (err) {
      console.error(err);
      setError(
        err.response?.data?.message || 
        'An error occurred. Please verify your credentials and try again.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container animate-fade-in">
      <div className="glass-panel auth-card">
        <div className="auth-header">
          <div className="auth-badge-icon">
            <ShieldCheck size={30} color="#0A0A0A" />
          </div>
          <h2 className="auth-title">
            {isLogin ? 'Welcome Back' : 'Get Started'}
          </h2>
          <p className="auth-subtitle">
            {isLogin ? 'Sign in to continue your interview journey' : 'Create an account to begin practicing'}
          </p>
        </div>

        {error && (
          <div className="alert-message alert-danger">
            {error}
          </div>
        )}

        {message && (
          <div className="alert-message alert-success">
            {message}
          </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div className="form-group">
            <label className="form-label">Username</label>
            <div className="input-wrapper">
              <User size={18} className="input-icon" />
              <input
                id="auth-username"
                type="text"
                className="premium-input"
                placeholder="Enter your username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
                autoComplete="username"
              />
            </div>
          </div>

          {!isLogin && (
            <div className="form-group">
              <label className="form-label">Email Address</label>
              <div className="input-wrapper">
                <Mail size={18} className="input-icon" />
                <input
                  id="auth-email"
                  type="email"
                  className="premium-input"
                  placeholder="name@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  autoComplete="email"
                />
              </div>
            </div>
          )}

          <div className="form-group">
            <label className="form-label">Password</label>
            <div className="input-wrapper">
              <Lock size={18} className="input-icon" />
              <input
                id="auth-password"
                type="password"
                className="premium-input"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                autoComplete="current-password"
              />
            </div>
          </div>

          <button 
            id="auth-submit"
            type="submit" 
            className="btn btn-primary auth-submit-btn" 
            disabled={loading}
          >
            {loading ? (
              <Loader2 className="animate-spin" size={20} />
            ) : (
              <>
                {isLogin ? 'Sign In' : 'Create Account'}
                <ArrowRight size={18} />
              </>
            )}
          </button>
        </form>

        <div className="auth-footer">
          {isLogin ? "Don't have an account? " : "Already have an account? "}
          <button
            onClick={() => {
              setIsLogin(!isLogin);
              setError('');
              setMessage('');
            }}
            className="auth-toggle-btn"
          >
            {isLogin ? 'Sign Up' : 'Sign In'}
          </button>
        </div>
      </div>
    </div>
  );
}
