import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Intercept requests to inject JWT auth header
api.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.accessToken) {
      config.headers['Authorization'] = 'Bearer ' + user.accessToken;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Intercept responses to handle auth errors (e.g. expired token)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('user');
      window.location.reload();
    }
    return Promise.reject(error);
  }
);

export const authService = {
  login: async (username, password) => {
    const response = await api.post('/auth/signin', { username, password });
    if (response.data.accessToken) {
      localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
  },
  signup: async (username, email, password) => {
    const response = await api.post('/auth/signup', { username, email, password });
    return response.data;
  },
  logout: () => {
    localStorage.removeItem('user');
    window.location.reload();
  },
  getCurrentUser: () => {
    return JSON.parse(localStorage.getItem('user'));
  }
};

export const interviewService = {
  createInterview: async (techStack, experienceLevel, numQuestions) => {
    const response = await api.post('/interviews/config', {
      techStack,
      experienceLevel,
      numQuestions
    });
    return response.data;
  },
  submitAnswer: async (questionId, userAnswer) => {
    const response = await api.post('/interviews/submit', {
      questionId,
      userAnswer
    });
    return response.data;
  },
  getDashboardStats: async () => {
    const response = await api.get('/interviews/dashboard');
    return response.data;
  },
  getHistory: async () => {
    const response = await api.get('/interviews/history');
    return response.data;
  },
  getDetails: async (sessionId) => {
    const response = await api.get(`/interviews/${sessionId}`);
    return response.data;
  }
};

export default api;
