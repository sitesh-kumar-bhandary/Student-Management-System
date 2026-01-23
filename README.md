# 🎓 Student Management System

A backend-focused Student Management System built using Spring Boot, designed to efficiently manage students, courses, enrollments, and reports with secure authentication, pagination, and performance validation through load testing.
This project emphasizes real-world backend engineering practices such as role-based security, pagination at scale, unit testing, and load testing using Apache JMeter.

## 📌 Overview

The system exposes RESTful APIs to manage students, courses, enrollments, and academic reports.
It supports secure access through authentication, efficient handling of large datasets using pagination, and performance validation with concurrent users.
The project was tested with 10,000+ student records, focusing on pagination stability, concurrency handling, and error-free request processing.

### 🛠 Tech Stack
#### Backend: Spring Boot
#### Language: Java
#### ORM: Spring Data JPA
#### Security: Spring Security (Role-Based Access Control)
#### Testing: JUnit
#### Load Testing: Apache JMeter
#### API Documentation: Swagger / OpenAPI
#### Utilities: Lombok
#### Build Tool: Maven

### ✨ Key Features

- Secure authentication and password management
- CRUD operations for students and courses
- Student enrollment management
- Paginated APIs for large datasets
- Search and reporting endpoints
- Multipart file upload support (student profile photo)
- Swagger-based API documentation
- Unit-tested service layer with measured code coverage

### 🔐 Authentication & Security

- Implemented Spring Security with role-based access control
- Protected sensitive student information
- Supported ~300–500 concurrent users during load testing
- Secured endpoints based on user roles

### 📌 API Endpoints

All APIs are RESTful and secured using Spring Security with role-based access control.

---
#### 🔐 Authentication (`/auth`)

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| POST | `/auth/login` | Authenticate user and return access token |
| POST | `/auth/change-password` | Change authenticated user password |

---

#### 📚 Courses (`/courses`)

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| POST | `/courses` | Create a new course |
| PUT | `/courses/{courseId}` | Update course details |
| DELETE | `/courses/{courseId}` | Delete a course |
| GET | `/courses` | Retrieve all courses |
| GET | `/courses/{courseId}` | Retrieve course by ID |

---

#### 📝 Enrollments (`/enrollments`)

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| POST | `/enrollments` | Enroll a student in a course |
| PUT | `/enrollments/{enrollmentId}` | Update enrollment details |
| DELETE | `/enrollments` | Remove a student enrollment |

---

#### 📊 Reports (`/reports`)

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| GET | `/reports` | Generate summary reports |
| GET | `/reports/courses/average-grade` | Retrieve average grade per course |

---

#### 🎓 Students (`/students`)

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| POST | `/students` | Create a student |
| PUT | `/students/{studentId}` | Update student details |
| DELETE | `/students/{studentId}` | Delete student |
| GET | `/students/{studentId}` | Retrieve student by ID |
| GET | `/students` | Retrieve all students (paginated) |
| GET | `/students/me` | Retrieve logged-in student profile |
| GET | `/students/search` | Search students by filters |
| POST | `/students/{studentId}/upload-photo` | Upload student profile photo |

---

### 📄 API Documentation (Swagger)
All APIs are documented and testable via Swagger UI.
```
http://localhost:8080/swagger-ui/index.html
```

### ⚙️ Performance & Load Testing
- Load-tested student listing APIs with 10,000+ records using Apache JMeter
- Implemented pagination to prevent full-table scans
- Identified and fixed:
  - Pagination boundary issues
  - CSV EOF issues during concurrent requests
- Achieved:
  - ~35–40% improvement in response times for bulk fetch APIs
  - 0% error rate after stabilizing pagination logic

### 🧪 Testing
- Implemented unit tests using JUnit
- Focused on:
  - Service-layer business logic
  - Pagination edge cases
  - Error handling scenarios
- Achieved ~54% code coverage, prioritizing critical application paths

### 🚀 Getting Started
#### Prerequisites
- Java 17+
- Maven
- MySQL / PostgreSQL (or configured database)
- Git

#### Run Locally
```
git clone https://github.com/your-username/student-management-system.git
cd student-management-system
mvn clean install
mvn spring-boot:run
```

### Load Testing (JMeter) - Pictures

<img width="1920" height="1080" alt="Screenshot (93)" src="https://github.com/user-attachments/assets/5b1c0608-6143-4e09-86d0-727243f0ffc6" />
<img width="1920" height="1080" alt="Screenshot (92)" src="https://github.com/user-attachments/assets/d86bfbe0-5695-4cee-8088-87a545a6a583" />
<img width="1920" height="1080" alt="Screenshot (91)" src="https://github.com/user-attachments/assets/62403495-b4c3-432e-b66e-55fb1f30322d" />
<img width="1920" height="1080" alt="Screenshot (90)" src="https://github.com/user-attachments/assets/1bb80292-71ab-4c26-9e82-257acc2a01a4" />


### JaCoco Report - Picture

<img width="1920" height="1080" alt="Screenshot (82)" src="https://github.com/user-attachments/assets/896ed23c-9495-4463-9094-9039039c7fd7" />


### 📈 Future Enhancements
- Add integration tests using MockMvc or Testcontainers
- Introduce caching for frequently accessed endpoints
- Add audit logging and monitoring
- Dockerize the application for deployment

### 👤 Author
#### Sitesh Kumar
##### Backend Developer | Java | Spring Boot
