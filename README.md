<div align="center">

# 🎯 MockMate

### AI-Powered Mock Interview Platform

Practice technical interviews with AI-generated questions and get instant, detailed feedback on your answers.

**Java/Spring Boot · React.js · System Design · Python**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19-61DAFB?style=flat-square&logo=react&logoColor=black)](https://react.dev)
[![Vite](https://img.shields.io/badge/Vite-8-646CFF?style=flat-square&logo=vite&logoColor=white)](https://vitejs.dev)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org)
[![License](https://img.shields.io/badge/License-MIT-white?style=flat-square)](LICENSE)

</div>

---

## 📖 About

**MockMate** is a full-stack AI-powered mock interview platform that helps developers practice technical interviews across multiple technology stacks. The platform generates scenario-based questions tailored to your experience level, evaluates your answers in real-time, and provides detailed feedback with strengths, areas for improvement, and reference answers.

### Key Features

- 🔐 **Secure Authentication** — JWT-based signup/login with Spring Security
- 🧠 **AI Question Generation** — Custom questions based on tech stack, experience level, and count
- ✍️ **Live Interview Sessions** — Answer questions one-by-one with a progress tracker
- 📊 **Instant AI Evaluation** — Get scored on each answer with strengths & improvement suggestions
- 📈 **Performance Dashboard** — Track total practices, completion rate, and average scores
- 📋 **Interview History** — Resume in-progress sessions or review completed ones
- 🌙 **Premium Dark UI** — Monochrome luxury design with glassmorphism and micro-animations
- 📱 **Fully Responsive** — Optimized for desktop, tablet, and mobile screens

---

## 🛠️ Tech Stack

### Backend
| Technology | Purpose |
|---|---|
| **Java 21** | Core language |
| **Spring Boot 3.3.5** | Application framework |
| **Spring Security** | Authentication & authorization |
| **Spring Data JPA** | Database ORM |
| **H2 Database** | In-memory DB (development) |
| **PostgreSQL** | Production database |
| **JWT (jjwt 0.12.5)** | Token-based authentication |
| **Maven** | Build & dependency management |

### Frontend
| Technology | Purpose |
|---|---|
| **React 19** | UI framework |
| **Vite 8** | Build tool & dev server |
| **Axios** | HTTP client for API calls |
| **Lucide React** | Icon library |
| **Vanilla CSS** | Custom design system (no frameworks) |

---

## 📁 Project Structure

```
MockMate/
├── backend/                          # Spring Boot Backend
│   ├── src/main/java/com/mock/interview/
│   │   ├── InterviewApplication.java # Application entry point
│   │   ├── config/
│   │   │   ├── SecurityConfig.java   # Spring Security & JWT config
│   │   │   └── WebConfig.java        # CORS configuration
│   │   ├── controller/
│   │   │   ├── AuthController.java   # Login & signup endpoints
│   │   │   └── InterviewController.java # Interview CRUD endpoints
│   │   ├── dto/                      # Data Transfer Objects
│   │   │   ├── AnswerSubmitRequest.java
│   │   │   ├── DashboardStats.java
│   │   │   ├── InterviewConfigRequest.java
│   │   │   ├── InterviewDetailsResponse.java
│   │   │   ├── JwtResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── SignupRequest.java
│   │   │   └── ...
│   │   ├── entity/                   # JPA Entities
│   │   │   ├── User.java
│   │   │   ├── InterviewSession.java
│   │   │   ├── Question.java
│   │   │   └── AnswerFeedback.java
│   │   ├── repository/              # Spring Data Repositories
│   │   │   ├── UserRepository.java
│   │   │   ├── InterviewSessionRepository.java
│   │   │   ├── QuestionRepository.java
│   │   │   └── AnswerFeedbackRepository.java
│   │   ├── security/                # JWT & Auth Components
│   │   │   ├── JwtUtils.java
│   │   │   ├── AuthTokenFilter.java
│   │   │   ├── AuthEntryPointJwt.java
│   │   │   ├── UserDetailsImpl.java
│   │   │   └── UserDetailsServiceImpl.java
│   │   └── service/                 # Business Logic
│   │       ├── AiService.java       # AI question generation & evaluation
│   │       └── InterviewService.java # Interview session management
│   ├── src/main/resources/
│   │   └── application.yml          # App configuration (dev & prod profiles)
│   ├── pom.xml                      # Maven dependencies
│   └── mvnw / mvnw.cmd             # Maven wrapper
│
├── frontend/                        # React + Vite Frontend
│   ├── public/
│   │   ├── favicon.svg
│   │   └── icons.svg
│   ├── src/
│   │   ├── components/
│   │   │   ├── AuthView.jsx         # Login & signup form
│   │   │   ├── ConfigView.jsx       # Interview configuration
│   │   │   ├── DashboardView.jsx    # Stats & interview history
│   │   │   ├── FeedbackView.jsx     # Performance report & scores
│   │   │   ├── Navbar.jsx           # Navigation bar
│   │   │   └── SessionView.jsx      # Live interview session
│   │   ├── services/
│   │   │   └── api.js               # Axios API client & auth service
│   │   ├── App.jsx                  # Root component & routing
│   │   ├── App.css
│   │   ├── index.css                # Complete design system
│   │   └── main.jsx                 # React entry point
│   ├── index.html                   # HTML entry with Inter font
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Node.js 18+** and **npm**
- **Maven** (or use the included wrapper)

### 1. Clone the Repository

```bash
git clone https://github.com/Sarjoshi2123/MockMate.git
cd MockMate
```

### 2. Start the Backend

```bash
cd backend
./mvnw spring-boot:run
```

> The backend starts on **http://https://mockmate-jwoj.onrender.com:8080** with an H2 in-memory database.
> H2 Console available at http://https://mockmate-jwoj.onrender.com:8080/h2-console

### 3. Start the Frontend

```bash
cd frontend
npm install
npm run dev
```

> The frontend starts on **http://https://mockmate-jwoj.onrender.com:5173**

### 4. Open the App

Navigate to **http://https://mockmate-jwoj.onrender.com:5173** in your browser, create an account, and start practicing!

---

## 🔌 API Endpoints

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/signup` | Register a new user |
| `POST` | `/api/auth/signin` | Login & receive JWT token |

### Interviews
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/interviews/config` | Create interview & generate AI questions |
| `POST` | `/api/interviews/submit` | Submit answer for AI evaluation |
| `GET` | `/api/interviews/dashboard` | Get dashboard statistics |
| `GET` | `/api/interviews/history` | Get all interview sessions |
| `GET` | `/api/interviews/{id}` | Get detailed session with feedback |

> All interview endpoints require `Authorization: Bearer <JWT_TOKEN>` header.

---

## 🎨 Design

The UI follows a **dark monochrome luxury aesthetic** — entirely black, gray, and white with no color accents.

- **Dark glassmorphism** cards with backdrop blur
- **Inter** font family for modern developer feel
- **Smooth micro-animations** — fade-ins, hover lifts, shimmer effects
- **Responsive breakpoints** at 1024px, 768px, and 480px
- **Custom CSS design system** — no Tailwind or Bootstrap, fully hand-crafted

---

## 📱 User Flow

```
Sign Up / Login  →  Dashboard  →  Configure Interview  →  Answer Questions  →  View Feedback
       ↑                                                                            |
       └────────────────────────────────────────────────────────────────────────────┘
```

1. **Authenticate** — Create an account or sign in
2. **Dashboard** — View your stats and past interview sessions
3. **Configure** — Pick a tech stack (Java, React, System Design, Python), experience level (Junior/Mid/Senior), and number of questions (1–10)
4. **Interview** — Answer AI-generated questions one at a time
5. **Feedback** — Review your overall score, per-question evaluation, strengths, improvement areas, and reference answers

---

## ⚙️ Configuration

### Environment Profiles

| Profile | Database | How to activate |
|---|---|---|
| `dev` (default) | H2 in-memory | Default, no config needed |
| `prod` | PostgreSQL | Set `spring.profiles.active=prod` + DB env vars |

### Production Environment Variables

```env
DB_HOST=https://mockmate-jwoj.onrender.com
DB_PORT=5432
DB_NAME=interviewdb
DB_USER=postgres
DB_PASSWORD=your_password
```

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

<div align="center">

**Built with ☕ and ⚛️ by [Sarjoshi2123](https://github.com/Sarjoshi2123)**

</div>
