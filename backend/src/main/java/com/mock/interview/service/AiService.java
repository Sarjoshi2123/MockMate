package com.mock.interview.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiService {
    private static final Logger logger = LoggerFactory.getLogger(AiService.class);

    // A structured repository of mock questions for high-fidelity fallback
    private static final Map<String, Map<String, List<MockQuestionTemplate>>> QUESTION_BANK = new HashMap<>();

    static {
        // Tech Stack: Java/Spring Boot
        Map<String, List<MockQuestionTemplate>> javaMap = new HashMap<>();
        javaMap.put("Junior", Arrays.asList(
            new MockQuestionTemplate(
                "Explain the difference between final, finally, and finalize in Java.",
                "final is a keyword to make variables immutable, classes un-extendable, and methods un-overridable. finally is a block used in exception handling that always executes. finalize is a protected method in the Object class called by the Garbage Collector before an object is destroyed (now deprecated).",
                Arrays.asList("finally", "immutable", "garbage", "exception")
            ),
            new MockQuestionTemplate(
                "What is the difference between HashMap and Hashtable in Java?",
                "HashMap is non-synchronized, not thread-safe, and allows one null key and multiple null values. Hashtable is synchronized, thread-safe, and does not allow any null keys or values.",
                Arrays.asList("synchronized", "thread", "safe", "null")
            ),
            new MockQuestionTemplate(
                "What is Spring Boot Dependency Injection, and what are its benefits?",
                "Dependency Injection (DI) is a design pattern that implements IoC (Inversion of Control) to pass dependencies to objects rather than creating them manually. Benefits include loose coupling, easier unit testing with mocks, and better maintainability.",
                Arrays.asList("coupling", "ioc", "inversion", "testing")
            ),
            new MockQuestionTemplate(
                "What are the main features of Java 8 and why are they significant?",
                "Java 8 introduced Lambda expressions for functional programming, the Stream API for efficient collection processing, the Optional class to handle nulls safely, default methods in interfaces, and the new Date/Time API (java.time). These features modernized Java development by enabling concise, functional-style code.",
                Arrays.asList("lambda", "stream", "optional", "functional", "interface")
            )
        ));
        javaMap.put("Mid", Arrays.asList(
            new MockQuestionTemplate(
                "How does Java's Garbage Collection work, and what are the generational zones (Eden, Survivor, Tenured)?",
                "Garbage Collection automatically reclaims heap memory. The Heap is split into Young Generation (Eden and Survivor spaces S0, S1) and Old Generation (Tenured). New objects are created in Eden. Minor GCs clean the Young Gen, copying surviving objects to Survivor spaces, and eventually promoting long-lived objects to Tenured. Major GCs clean the Old Generation.",
                Arrays.asList("eden", "survivor", "heap", "young", "old", "tenured", "generation")
            ),
            new MockQuestionTemplate(
                "Explain the Spring Bean Lifecycle. What are the key stages?",
                "The Spring Bean Lifecycle stages include: Instantiation, Populate Properties, BeanNameAware/BeanFactoryAware initialization, Pre-initialization BeanPostProcessors, Custom init method (afterPropertiesSet), Post-initialization BeanPostProcessors, Bean is ready for use, and Destruction (destroy method or DisposableBean).",
                Arrays.asList("instantiation", "postprocessor", "init", "destroy", "lifecycle")
            ),
            new MockQuestionTemplate(
                "What are the differences between optimistic locking and pessimistic locking in Spring Data JPA?",
                "Optimistic locking assumes collisions are rare and uses a @Version column to check for concurrent modifications; it throws ObjectOptimisticLockingFailureException on conflict. Pessimistic locking locks the database record directly (e.g. SELECT FOR UPDATE), blocking concurrent transactions until the lock is released.",
                Arrays.asList("version", "pessimistic", "optimistic", "lock", "conflict")
            ),
            new MockQuestionTemplate(
                "Explain the differences between @Component, @Service, @Repository, and @Controller annotations in Spring.",
                "All four are specializations of @Component. @Service indicates business logic layer. @Repository indicates the persistence/data access layer and adds automatic exception translation. @Controller marks web controllers handling HTTP requests. @Component is the generic stereotype for any Spring-managed component.",
                Arrays.asList("component", "stereotype", "persistence", "business", "controller")
            )
        ));
        javaMap.put("Senior", Arrays.asList(
            new MockQuestionTemplate(
                "Explain the Java Memory Model (JMM). What are the effects of the 'volatile' keyword and 'synchronized' locks?",
                "JMM defines how threads interact through memory. The 'volatile' keyword guarantees visibility (flushes variables to main memory directly, preventing local thread caches) and instruction reordering prevention (happens-before relationship). 'synchronized' provides mutual exclusion, thread safety, and memory visibility, enforcing that only one thread executes a block at a time.",
                Arrays.asList("visibility", "volatile", "happens", "cache", "thread", "memory")
            ),
            new MockQuestionTemplate(
                "How would you optimize a Spring Boot application suffering from high latency and N+1 query problems in Hibernate?",
                "To fix N+1 query problems, use Entity Graphs (@EntityGraph), JOIN FETCH queries, or @Fetch(FetchMode.SUBSELECT) to load relationships in a single database roundtrip. Other optimizations include enabling second-level cache, indexing frequently queried database columns, pagination, and utilizing asynchronous handling with CompletableFuture or WebFlux.",
                Arrays.asList("fetch", "graph", "join", "cache", "n+1", "index")
            ),
            new MockQuestionTemplate(
                "Describe how you would design a microservices architecture using Spring Cloud. What patterns and components would you use?",
                "Key components include: API Gateway (Spring Cloud Gateway) for routing, Service Discovery (Eureka/Consul) for dynamic registration, Config Server for centralized configuration, Circuit Breaker (Resilience4j) for fault tolerance, Distributed Tracing (Zipkin/Sleuth) for observability, and Message Brokers (RabbitMQ/Kafka) for async communication between services.",
                Arrays.asList("gateway", "eureka", "circuit", "config", "tracing", "discovery")
            )
        ));

        // Tech Stack: React.js
        Map<String, List<MockQuestionTemplate>> reactMap = new HashMap<>();
        reactMap.put("Junior", Arrays.asList(
            new MockQuestionTemplate(
                "What is the Virtual DOM, and how does React use it to render components?",
                "The Virtual DOM is an in-memory representation of the real DOM. When state changes, React creates a new virtual DOM tree, compares it with the old virtual DOM tree using a diffing algorithm (Reconciliation), and batch updates only the changed elements in the real DOM, improving performance.",
                Arrays.asList("virtual", "diffing", "reconciliation", "reconcile", "batch")
            ),
            new MockQuestionTemplate(
                "What is the difference between props and state in React?",
                "Props (properties) are read-only inputs passed from a parent component to a child component to configure it. State is a private, mutable data structure maintained internally within a component that triggers a re-render when modified via its setter function.",
                Arrays.asList("props", "state", "mutable", "parent", "internal")
            ),
            new MockQuestionTemplate(
                "Explain the useEffect hook and how the dependency array governs its execution.",
                "useEffect is used for side effects (API calls, subscriptions). If the dependency array is empty [], it runs once after mount. If it contains variables [a, b], it runs on mount and whenever those variables change. If there is no array, it runs after every render. A return function acts as a cleanup mechanism (runs before unmount or next effect).",
                Arrays.asList("mount", "dependency", "array", "cleanup", "effect")
            ),
            new MockQuestionTemplate(
                "What is JSX and why does React use it?",
                "JSX is a syntax extension that allows writing HTML-like code in JavaScript. React uses JSX because it provides a familiar, readable syntax for defining component UI, compiles to React.createElement() calls at build time, and enables embedding JavaScript expressions directly within the markup using curly braces.",
                Arrays.asList("jsx", "syntax", "createElement", "html", "expression")
            )
        ));
        reactMap.put("Mid", Arrays.asList(
            new MockQuestionTemplate(
                "What is React Context API, and how does it compare to state management libraries like Redux?",
                "React Context API provides a way to pass data through the component tree without prop drilling. It is great for low-frequency updates like themes or auth states. Redux is a predictable state container that stores global state in a single store, utilizing actions and reducers. Redux is better suited for complex, high-frequency updates, has excellent debugging tools, and supports middleware.",
                Arrays.asList("drilling", "context", "redux", "store", "reducer", "action")
            ),
            new MockQuestionTemplate(
                "How does React's reconciliation algorithm work? Why is the 'key' prop necessary in lists?",
                "Reconciliation is React's O(n) diffing algorithm. It assumes elements of different types produce different trees, and lists of child elements can be tracked using keys. The 'key' prop serves as a unique identifier that helps React recognize which items have changed, been added, or been removed, avoiding unnecessary re-renders and maintaining component state.",
                Arrays.asList("reconciliation", "diff", "key", "list", "render", "re-render")
            ),
            new MockQuestionTemplate(
                "Explain custom hooks in React. When would you create one and what rules do hooks follow?",
                "Custom hooks are JavaScript functions prefixed with 'use' that extract reusable stateful logic from components. They follow the Rules of Hooks: only call hooks at the top level (not inside loops/conditions), and only call them from React functions. Create custom hooks when multiple components share the same stateful logic, like useFetch for data fetching or useLocalStorage for persistent state.",
                Arrays.asList("custom", "hook", "reusable", "rules", "top level")
            )
        ));
        reactMap.put("Senior", Arrays.asList(
            new MockQuestionTemplate(
                "Explain React Concurrent Features (useTransition, useDeferredValue) and how Fiber architecture enables them.",
                "React Fiber is a rewrite of React's core rendering engine that splits rendering work into incremental units, allowing pausing, prioritizing, and discarding updates. Concurrent features like useTransition mark state updates as non-blocking transitions, allowing the UI to remain responsive. useDeferredValue defers re-rendering a portion of the tree to prevent lag during heavy computations.",
                Arrays.asList("fiber", "transition", "defer", "render", "responsive", "priority")
            ),
            new MockQuestionTemplate(
                "How would you structure a React application to optimize performance, prevent unnecessary re-renders, and manage large-scale forms?",
                "To optimize performance: Use React.memo for component caching, useMemo and useCallback to cache values/functions and preserve referential equality. For forms, use uncontrolled components (react-hook-form) to avoid re-rendering on every keystroke. Utilize virtualized lists (react-window) for large lists, lazy loading (React.lazy + Suspense), and throttle/debounce input handlers.",
                Arrays.asList("memo", "useCallback", "useMemo", "render", "virtualize", "form", "lazy", "suspense")
            )
        ));

        // Tech Stack: System Design
        Map<String, List<MockQuestionTemplate>> sysMap = new HashMap<>();
        List<MockQuestionTemplate> sysList = Arrays.asList(
            new MockQuestionTemplate(
                "Explain the CAP theorem and the trade-offs between Consistency, Availability, and Partition Tolerance.",
                "CAP theorem states that a distributed system can guarantee at most two of three properties: Consistency (every read receives the most recent write), Availability (every non-failing node returns a response), and Partition Tolerance (system continues to operate despite network messages dropping). In the event of a network partition, you must choose either Consistency (CP) or Availability (AP).",
                Arrays.asList("cap", "consistency", "availability", "partition", "distributed", "network")
            ),
            new MockQuestionTemplate(
                "How would you design a highly available, horizontally scalable notification system that handles push, email, and SMS?",
                "The architecture needs a Rate Limiter at the API Gateway, a Notification Service to validate requests, a Message Queue (Kafka or RabbitMQ) to decouple requests, Worker Services that consume messages and communicate with third-party delivery providers (APNS, Twilio, SendGrid), a database (PostgreSQL + Redis for caching templates/users), and an analytics service to log delivery status.",
                Arrays.asList("gateway", "queue", "kafka", "rabbitmq", "database", "cache", "redis", "scale", "worker")
            ),
            new MockQuestionTemplate(
                "What is database sharding? Compare it with replication and horizontal scaling.",
                "Database sharding is partitioning data horizontally across multiple database servers, where each shard holds a subset of the data (e.g. by hashing user_id). Replication is copying the entire database across multiple instances (e.g. Master-Slave) for high availability and read scalability. Sharding addresses write capacity constraints and storage size limits.",
                Arrays.asList("shard", "replication", "horizontal", "partition", "master", "slave", "scale")
            ),
            new MockQuestionTemplate(
                "Design a URL shortener like bit.ly. What components and data stores would you use?",
                "Use an API server to receive long URLs, generate a short hash (Base62 encoding of an auto-increment ID or MD5 hash), store the mapping in a NoSQL database (DynamoDB/Cassandra) or relational DB with caching (Redis). Add a redirect service that looks up the short URL, returns a 301/302 redirect. Include rate limiting, analytics tracking, and optional custom aliases.",
                Arrays.asList("hash", "base62", "redirect", "cache", "database", "analytics")
            )
        );
        sysMap.put("Junior", sysList.subList(0, 2));
        sysMap.put("Mid", sysList.subList(0, 3));
        sysMap.put("Senior", sysList);

        // Tech Stack: Python
        Map<String, List<MockQuestionTemplate>> pythonMap = new HashMap<>();
        pythonMap.put("Junior", Arrays.asList(
            new MockQuestionTemplate(
                "What is the difference between a list and a tuple in Python?",
                "Lists are mutable (can be modified after creation), use square brackets [], and are slightly slower. Tuples are immutable (cannot be changed), use parentheses (), and are faster. Tuples can be used as dictionary keys due to immutability, while lists cannot.",
                Arrays.asList("mutable", "immutable", "list", "tuple", "bracket")
            ),
            new MockQuestionTemplate(
                "What are Python decorators and how do they work?",
                "Decorators are functions that modify the behavior of another function without changing its code. They use the @decorator_name syntax placed above a function definition. Under the hood, @decorator is equivalent to func = decorator(func). Common uses include logging, authentication, caching, and measuring execution time.",
                Arrays.asList("decorator", "function", "wrapper", "syntax", "modify")
            )
        ));
        pythonMap.put("Mid", Arrays.asList(
            new MockQuestionTemplate(
                "Explain Python's Global Interpreter Lock (GIL) and its impact on multithreading.",
                "The GIL is a mutex that protects access to Python objects, preventing multiple threads from executing Python bytecodes simultaneously. This means CPU-bound multithreaded programs don't benefit from multiple cores. Workarounds include using multiprocessing (separate processes), C extensions, or async I/O for I/O-bound tasks.",
                Arrays.asList("gil", "mutex", "thread", "multiprocessing", "bytecode", "cpu")
            ),
            new MockQuestionTemplate(
                "What are generators in Python and how do they differ from regular functions?",
                "Generators are functions that use 'yield' instead of 'return' to produce a sequence of values lazily, one at a time. They maintain state between calls, use less memory than lists (since values are generated on-the-fly), and are iterable. Generator expressions provide a concise syntax similar to list comprehensions.",
                Arrays.asList("yield", "generator", "lazy", "memory", "iterable", "state")
            )
        ));
        pythonMap.put("Senior", pythonMap.get("Mid"));

        // Put to main bank
        QUESTION_BANK.put("Java/Spring Boot", javaMap);
        QUESTION_BANK.put("React.js", reactMap);
        QUESTION_BANK.put("System Design", sysMap);
        QUESTION_BANK.put("Python", pythonMap);
    }

    public List<String> generateQuestions(String techStack, String experienceLevel, int numQuestions) {
        logger.info("Generating {} questions for {} ({})", numQuestions, techStack, experienceLevel);
        return generateMockQuestions(techStack, experienceLevel, numQuestions);
    }

    private List<String> generateMockQuestions(String techStack, String experienceLevel, int numQuestions) {
        Map<String, List<MockQuestionTemplate>> stackMap = QUESTION_BANK.getOrDefault(techStack, QUESTION_BANK.get("Java/Spring Boot"));
        if (stackMap == null) stackMap = QUESTION_BANK.get("Java/Spring Boot");
        
        List<MockQuestionTemplate> templates = stackMap.getOrDefault(experienceLevel, stackMap.get("Junior"));
        if (templates == null) templates = stackMap.values().iterator().next();

        List<String> selected = new ArrayList<>();
        List<MockQuestionTemplate> pool = new ArrayList<>(templates);
        Collections.shuffle(pool);

        for (int i = 0; i < Math.min(numQuestions, pool.size()); i++) {
            selected.add(pool.get(i).question);
        }

        // If they requested more questions than we have in pool, add generic ones
        int extra = 1;
        while (selected.size() < numQuestions) {
            selected.add("Describe a challenging " + techStack + " project you worked on. What problems did you face and how did you solve them? (Scenario " + extra + ")");
            extra++;
        }

        return selected;
    }

    public Map<String, Object> evaluateAnswer(String question, String answer, String techStack, String experienceLevel) {
        logger.info("Evaluating answer for stack: {} ({})", techStack, experienceLevel);
        return evaluateAnswerMock(question, answer, techStack, experienceLevel);
    }

    private Map<String, Object> evaluateAnswerMock(String question, String answer, String techStack, String experienceLevel) {
        Map<String, Object> result = new HashMap<>();
        
        // Find corresponding mock question template if it exists
        MockQuestionTemplate matchedTemplate = null;
        for (Map.Entry<String, Map<String, List<MockQuestionTemplate>>> stackEntry : QUESTION_BANK.entrySet()) {
            for (Map.Entry<String, List<MockQuestionTemplate>> levelEntry : stackEntry.getValue().entrySet()) {
                for (MockQuestionTemplate temp : levelEntry.getValue()) {
                    if (temp.question.equalsIgnoreCase(question)) {
                        matchedTemplate = temp;
                        break;
                    }
                }
                if (matchedTemplate != null) break;
            }
            if (matchedTemplate != null) break;
        }

        // Default ideal answer if none found
        String ideal = (matchedTemplate != null) ? matchedTemplate.idealAnswer 
            : "An ideal answer should explain the conceptual framework, highlight real-world applications, and provide code examples showing safety and correctness.";
        
        if (answer == null || answer.trim().isEmpty()) {
            result.put("score", 0.0);
            result.put("strengths", "No answer was provided.");
            result.put("improvements", "Please attempt the question by writing a structured explanation, naming key concepts, and providing coding examples where applicable.");
            result.put("ideal", ideal);
            return result;
        }

        String cleanedAnswer = answer.toLowerCase();
        
        // Basic keywords match scoring
        double baseScore = 30.0;
        List<String> keywordsMatched = new ArrayList<>();
        List<String> keywordsMissed = new ArrayList<>();
        
        if (matchedTemplate != null && matchedTemplate.keywords != null) {
            for (String kw : matchedTemplate.keywords) {
                if (cleanedAnswer.contains(kw.toLowerCase())) {
                    keywordsMatched.add(kw);
                } else {
                    keywordsMissed.add(kw);
                }
            }
            
            // Adjust score dynamically based on keyword match percentage
            if (!matchedTemplate.keywords.isEmpty()) {
                double matchRatio = (double) keywordsMatched.size() / matchedTemplate.keywords.size();
                baseScore += matchRatio * 60.0; // 30 base + max 60 = 90
            }
        } else {
            // General heuristics for custom/generic questions
            if (cleanedAnswer.length() > 200) baseScore += 40;
            else if (cleanedAnswer.length() > 50) baseScore += 20;
            
            if (cleanedAnswer.contains("example") || cleanedAnswer.contains("code") || cleanedAnswer.contains("instance")) {
                baseScore += 15;
            }
            if (cleanedAnswer.contains("because") || cleanedAnswer.contains("differ") || cleanedAnswer.contains("compare")) {
                baseScore += 10;
            }
        }
        
        // Cap and adjust based on answer length
        if (cleanedAnswer.length() > 300) {
            baseScore = Math.min(baseScore + 5, 98.0);
        } else {
            baseScore = Math.min(baseScore, 90.0);
        }
        
        result.put("score", Math.round(baseScore * 10.0) / 10.0);

        // Generate strengths/improvements text
        if (baseScore < 45.0) {
            result.put("strengths", "Attempted the answer and wrote a basic definition.");
            result.put("improvements", "Your answer is too brief. Try to cover technical terms. Missed key concepts: " + 
                       (keywordsMissed.isEmpty() ? "conceptual details, real-world examples" : String.join(", ", keywordsMissed)) + ".");
        } else if (baseScore < 75.0) {
            result.put("strengths", "Demonstrated initial understanding and mentioned key concepts like " + 
                       (keywordsMatched.isEmpty() ? "related terms" : String.join(", ", keywordsMatched)) + ".");
            result.put("improvements", "Expand on the operational mechanics. Consider adding " + 
                       (keywordsMissed.isEmpty() ? "a step-by-step example" : "information on: " + String.join(", ", keywordsMissed)) + ".");
        } else {
            result.put("strengths", "Excellent detail! Covered crucial terms such as " + String.join(", ", keywordsMatched) + " and explained the mechanics clearly.");
            result.put("improvements", keywordsMissed.isEmpty() 
                ? "Virtually perfect. Review code syntax and edge cases to polish your delivery." 
                : "For a top score, also clarify: " + String.join(", ", keywordsMissed) + ".");
        }
        
        result.put("ideal", ideal);
        return result;
    }

    private static class MockQuestionTemplate {
        String question;
        String idealAnswer;
        List<String> keywords;

        MockQuestionTemplate(String question, String idealAnswer, List<String> keywords) {
            this.question = question;
            this.idealAnswer = idealAnswer;
            this.keywords = keywords;
        }
    }
}
