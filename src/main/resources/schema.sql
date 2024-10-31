CREATE TABLE IF NOT EXISTS student (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        first_name VARCHAR(50),
        last_name VARCHAR(50),
        email VARCHAR(100),
        field_of_study VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS course (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100),
        hours INT,
        max_students INT
    );

CREATE TABLE IF NOT EXISTS lecture (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        lecturer_name VARCHAR(100),
        field_of_study VARCHAR(50),
        course_id BIGINT,
        FOREIGN KEY (course_id) REFERENCES course(id)
    );

CREATE TABLE IF NOT EXISTS student_course (
        student_id BIGINT,
        course_id BIGINT,
        PRIMARY KEY (student_id, course_id),
        FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
        FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
    );
