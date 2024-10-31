-- Insert students without specifying IDs
INSERT INTO student (first_name, last_name, email, field_of_study) VALUES
    ('John', 'Doe', 'john.doe@example.com', 'Computer Science'),
    ('Jane', 'Smith', 'jane.smith@example.com', 'Engineering'),
    ('Bob', 'Brown', 'bob.brown@example.com', 'Mathematics'),
    ('Alice', 'White', 'alice.white@example.com', 'Physics'),
    ('Tom', 'Black', 'tom.black@example.com', 'Biology');

-- Insert courses without specifying IDs
INSERT INTO course (name, hours, max_students) VALUES
    ('Calculus', 40, 30),
    ('Algorithms', 30, 25),
    ('Physics 101', 50, 35),
    ('Data Structures', 45, 40),
    ('Biology Basics', 35, 20);

-- Insert relationships into the student_course join table using dynamically retrieved IDs

-- John Doe's courses
INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'John' AND s.last_name = 'Doe' AND c.name = 'Algorithms';

INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'John' AND s.last_name = 'Doe' AND c.name = 'Data Structures';

-- Jane Smith's courses
INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'Jane' AND s.last_name = 'Smith' AND c.name = 'Algorithms';

INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'Jane' AND s.last_name = 'Smith' AND c.name = 'Calculus';

-- Bob Brown's courses
INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'Bob' AND s.last_name = 'Brown' AND c.name = 'Calculus';

-- Alice White's courses
INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'Alice' AND s.last_name = 'White' AND c.name = 'Physics 101';

INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'Alice' AND s.last_name = 'White' AND c.name = 'Data Structures';

-- Tom Black's courses
INSERT INTO student_course (student_id, course_id)
SELECT s.id, c.id FROM student s, course c
WHERE s.first_name = 'Tom' AND s.last_name = 'Black' AND c.name = 'Biology Basics';


-- Insert lectures with lecturer names and fields of study, linked to courses
INSERT INTO lecture (lecturer_name, field_of_study, course_id) VALUES
    ('Dr. Alice Green', 'Mathematics', (SELECT id FROM course WHERE name = 'Calculus')),
    ('Dr. Bob White', 'Mathematics', (SELECT id FROM course WHERE name = 'Calculus')),
    ('Prof. Charles Brown', 'Computer Science', (SELECT id FROM course WHERE name = 'Algorithms')),
    ('Dr. Diana Grey', 'Physics', (SELECT id FROM course WHERE name = 'Physics 101')),
    ('Prof. Eric Black', 'Computer Science', (SELECT id FROM course WHERE name = 'Data Structures')),
    ('Dr. Fiona Blue', 'Biology', (SELECT id FROM course WHERE name = 'Biology Basics'));
