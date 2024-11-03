package com.acum.schoolmanagement.controller;

import com.acum.schoolmanagement.dto.CreateStudentDto;
import com.acum.schoolmanagement.dto.StudentDto;
import com.acum.schoolmanagement.dto.UpdateStudentDto;
import com.acum.schoolmanagement.entity.Student;
import com.acum.schoolmanagement.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        StudentDto studentDto = studentService.getStudentById(id);
        return ResponseEntity.ok(studentDto);
//        return studentService.getStudentById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@Valid @RequestBody CreateStudentDto createStudentDto) {
        Student savedStudent = studentService.addStudent(createStudentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentDto updateStudentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, updateStudentDto));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

}
