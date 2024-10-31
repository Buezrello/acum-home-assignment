package com.acum.schoolmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lecturerName;
    private String fieldOfStudy;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
