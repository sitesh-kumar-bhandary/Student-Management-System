INSERT INTO students (student_name, email, version) VALUES
('Rahul', 'rahul1@gmail.com', 0),
('Rahul Sharma', 'rahul2@gmail.com', 0),
('Ankit', 'ankit@gmail.com', 0),
('Neha', 'neha@gmail.com', 0),
('Amit', 'amit@gmail.com', 0),
('Rohit', 'rohit@gmail.com', 0),
('Suresh', 'suresh@gmail.com', 0),
('Pooja', 'pooja@gmail.com', 0),
('Karan', 'karan@gmail.com', 0),
('Sneha', 'sneha@gmail.com', 0),
('Ravi', 'ravi@gmail.com', 0),
('Vikas', 'vikas@gmail.com', 0),
('Arjun', 'arjun@gmail.com', 0),
('Manish', 'manish@gmail.com', 0),
('Divya', 'divya@gmail.com', 0),
('Priya', 'priya@gmail.com', 0),
('Nitin', 'nitin@gmail.com', 0),
('Sahil', 'sahil@gmail.com', 0),
('Akash', 'akash@gmail.com', 0),
('Shubham', 'shubham@gmail.com', 0),
('Rahul Verma', 'rahul3@gmail.com', 0),
('Rahul Mehta', 'rahul4@gmail.com', 0),
('Deepak', 'deepak@gmail.com', 0),
('Ayesha', 'ayesha@gmail.com', 0),
('Mohit', 'mohit@gmail.com', 0);


INSERT INTO courses (course_name, code, version) VALUES
('Data Structures', 'CS101', 0),
('Algorithms', 'CS102', 0),
('Operating Systems', 'CS103', 0),
('Database Systems', 'CS104', 0),
('Computer Networks', 'CS105', 0),
('Java Programming', 'CS106', 0),
('Spring Boot', 'CS107', 0),
('Microservices', 'CS108', 0),
('System Design', 'CS109', 0),
('Cloud Computing', 'CS110', 0),
('Machine Learning', 'CS111', 0),
('Artificial Intelligence', 'CS112', 0),
('Web Development', 'CS113', 0),
('Distributed Systems', 'CS114', 0),
('DevOps', 'CS115', 0),
('Docker & Kubernetes', 'CS116', 0),
('Python Programming', 'CS117', 0),
('C++ Programming', 'CS118', 0),
('Software Testing', 'CS119', 0),
('Cyber Security', 'CS120', 0),
('Data Analytics', 'CS121', 0),
('Big Data', 'CS122', 0),
('Blockchain', 'CS123', 0),
('Mobile App Development', 'CS124', 0),
('Linux Fundamentals', 'CS125', 0);


INSERT INTO enrollments (grade, enrollment_date, version, student_id, course_id) VALUES
(85.5, '2024-01-10', 0, 1, 1),
(90.0, '2024-01-10', 0, 1, 2),
(78.0, '2024-01-10', 0, 1, 6),

(88.0, '2024-01-11', 0, 2, 1),
(82.5, '2024-01-11', 0, 2, 3),

(75.0, '2024-01-12', 0, 3, 4),
(80.0, '2024-01-12', 0, 3, 5),

(92.0, '2024-01-13', 0, 4, 6),
(89.0, '2024-01-13', 0, 4, 7),

(86.0, '2024-01-14', 0, 5, 8),
(91.0, '2024-01-14', 0, 5, 9),

(84.0, '2024-01-15', 0, 6, 10),
(88.5, '2024-01-15', 0, 6, 11),

(79.0, '2024-01-16', 0, 7, 12),
(83.5, '2024-01-16', 0, 7, 13),

(90.0, '2024-01-17', 0, 8, 14),
(87.0, '2024-01-17', 0, 8, 15),

(76.0, '2024-01-18', 0, 9, 16),
(81.0, '2024-01-18', 0, 9, 17),

(85.0, '2024-01-19', 0, 10, 18),
(88.0, '2024-01-19', 0, 10, 19),

(92.0, '2024-01-20', 0, 21, 6),
(89.5, '2024-01-20', 0, 21, 7),

(84.0, '2024-01-21', 0, 22, 8),
(90.0, '2024-01-21', 0, 22, 9);