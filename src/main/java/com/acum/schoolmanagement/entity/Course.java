package com.acum.schoolmanagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int hours;
    private int maxStudents;

    @OneToMany(mappedBy = "course")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Lecture> lectures;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Student> students = new HashSet<>();

}
