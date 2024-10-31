package com.acum.schoolmanagement.repository;

import com.acum.schoolmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT c.id as course_id, c.name as course_name, c.hours as course_hours, " +
            "c.max_students as course_max_students, COUNT(sc.student_id) as student_count, " +
            "l.id as lecture_id, l.lecturer_name, l.field_of_study " +
            "FROM course c " +
            "LEFT JOIN student_course sc ON c.id = sc.course_id " +
            "LEFT JOIN lecture l ON l.course_id = c.id " +
            "GROUP BY c.id, c.name, c.hours, c.max_students, l.id, l.lecturer_name, l.field_of_study",
            nativeQuery = true)
    List<Object[]> findAllWithStudentCountAndLecturesNative();

}

