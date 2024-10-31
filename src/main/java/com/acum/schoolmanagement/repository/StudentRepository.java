package com.acum.schoolmanagement.repository;

import com.acum.schoolmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Fetch single student by ID with courses
    @Query(value = "SELECT s.id as student_id, s.first_name, s.last_name, s.email, s.field_of_study, " +
            "c.id as course_id, c.name as course_name, c.hours, c.max_students " +
            "FROM student s  LEFT JOIN student_course sc ON s.id = sc.student_id " +
            "LEFT JOIN course c ON c.id = sc.course_id  WHERE s.id = :id", nativeQuery = true)
    List<Object[]> findByIdWithCoursesNative(@Param("id") Long id);


    // Fetch all students with their courses
    @Query(value = "SELECT s.id as student_id, s.first_name, s.last_name, s.email, s.field_of_study, " +
            "c.id as course_id, c.name as course_name, c.hours as course_hours, c.max_students as course_max_students " +
            "FROM student s " +
            "LEFT JOIN student_course sc ON s.id = sc.student_id " +
            "LEFT JOIN course c ON c.id = sc.course_id",
            nativeQuery = true)
    List<Object[]> findAllWithCoursesNative();

    @Modifying
    @Query(value = "DELETE FROM student_course WHERE student_id = :studentId", nativeQuery = true)
    void deleteCoursesByStudentId(@Param("studentId") Long studentId);


}
