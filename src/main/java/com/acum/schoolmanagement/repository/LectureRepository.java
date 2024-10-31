package com.acum.schoolmanagement.repository;

import com.acum.schoolmanagement.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
