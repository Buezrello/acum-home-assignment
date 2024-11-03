package com.acum.schoolmanagement.service;

import com.acum.schoolmanagement.advice.ResourceNotFoundException;
import com.acum.schoolmanagement.dto.CourseDto;
import com.acum.schoolmanagement.dto.CreateStudentDto;
import com.acum.schoolmanagement.dto.StudentDto;
import com.acum.schoolmanagement.dto.UpdateStudentDto;
import com.acum.schoolmanagement.entity.Course;
import com.acum.schoolmanagement.entity.Student;
import com.acum.schoolmanagement.repository.CourseRepository;
import com.acum.schoolmanagement.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    // Fetch student by id with courses
    public StudentDto getStudentById(Long id) {
        List<Object[]> results = studentRepository.findByIdWithCoursesNative(id);

        if (results.isEmpty()) {
            throw new ResourceNotFoundException("Student with ID " + id + " not found");
        }

        Object[] firstRow = results.get(0);
        StudentDto studentDto = new StudentDto(
                (Long) firstRow[0],  // student_id
                (String) firstRow[1], // first_name
                (String) firstRow[2], // last_name
                (String) firstRow[3], // email
                (String) firstRow[4]  // field_of_study
        );

        // Add courses from each row
        for (Object[] row : results) {
            Long courseId = (Long) row[5]; // course_id
            if (courseId != null) {
                CourseDto courseDto = new CourseDto(
                        courseId,
                        (String) row[6],  // course_name
                        (Integer) row[7], // hours
                        (Integer) row[8]  // max_students
                );
                studentDto.getCourses().add(courseDto);
            }
        }

        return studentDto;
    }

    // Add new student
    public Student addStudent(CreateStudentDto createStudentDto) {
        // Validate that courseIds are provided
        if (createStudentDto.getCourseIds() == null || createStudentDto.getCourseIds().isEmpty()) {
            throw new IllegalArgumentException("Course IDs must not be null or empty");
        }

        Student student = convertStudentDtoToStudentEntity(createStudentDto);

        // Load Course entities by IDs and set them in the student entity
        List<Course> courses = courseRepository.findAllById(createStudentDto.getCourseIds());
        if (courses.isEmpty() && !createStudentDto.getCourseIds().isEmpty()) {
            throw new IllegalArgumentException("No courses found for provided IDs");
        }
        student.setCourses(new HashSet<>(courses));  // Assuming Student has Set<Course> courses

        // Save and return the student entity
        return studentRepository.save(student);
    }

    // Edit student information
    @Transactional  
    public Student updateStudent(Long id, UpdateStudentDto updateStudentDto) {
        // Retrieve the existing student from the database
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found"));

        // Update fields only if they are present in the request
        if (updateStudentDto.getFirstName() != null) {
            existingStudent.setFirstName(updateStudentDto.getFirstName());
        }
        if (updateStudentDto.getLastName() != null) {
            existingStudent.setLastName(updateStudentDto.getLastName());
        }
        if (updateStudentDto.getEmail() != null) {
            existingStudent.setEmail(updateStudentDto.getEmail());
        }
        if (updateStudentDto.getFieldOfStudy() != null) {
            existingStudent.setFieldOfStudy(updateStudentDto.getFieldOfStudy());
        }

        // Handle course updates
        if (updateStudentDto.getCourseIds() != null && !updateStudentDto.getCourseIds().isEmpty()) {
            List<Course> newCourses = courseRepository.findAllById(updateStudentDto.getCourseIds());

            if (newCourses.size() == updateStudentDto.getCourseIds().size()) {
                // Only update courses if all course IDs are valid
                studentRepository.deleteCoursesByStudentId(id);
                existingStudent.setCourses(new HashSet<>(newCourses));
            } else {
                throw new IllegalArgumentException("One or more provided course IDs are invalid.");
            }
        }

        // Save and return the updated student entity
        return studentRepository.save(existingStudent);
    }


    private static Student convertStudentDtoToStudentEntity(CreateStudentDto createStudentDto) {
        // Convert CreateStudentDto to Student entity
        Student student = new Student();
        student.setFirstName(createStudentDto.getFirstName());
        student.setLastName(createStudentDto.getLastName());
        student.setEmail(createStudentDto.getEmail());
        student.setFieldOfStudy(createStudentDto.getFieldOfStudy());
        return student;
    }

    // List all students
    public List<StudentDto> getAllStudents() {
        List<Object[]> results = studentRepository.findAllWithCoursesNative();
        Map<Long, StudentDto> studentDtoMap = new HashMap<>();

        for (Object[] row : results) {
            Long studentId = ((Number) row[0]).longValue();
            // Fetch or create the StudentDto for the current student ID
            StudentDto studentDto = studentDtoMap.computeIfAbsent(studentId, id -> new StudentDto(
                    studentId,
                    (String) row[1],   // first_name
                    (String) row[2],   // last_name
                    (String) row[3],   // email
                    (String) row[4],   // field_of_study
                    new ArrayList<>()  // Initialize empty list of courses
            ));

            // If the course data exists in the row, map it to CourseDto and add to studentDto's course list
            Long courseId = (row[5] != null) ? ((Number) row[5]).longValue() : null;
            if (courseId != null) {
                CourseDto courseDto = new CourseDto(
                        courseId,
                        (String) row[6],      // course_name
                        (Integer) row[7],     // hours
                        (Integer) row[8]      // max_students
                );
                studentDto.getCourses().add(courseDto);
            }
        }

        // Return all StudentDto objects with their courses
        return new ArrayList<>(studentDtoMap.values());

    }

}

