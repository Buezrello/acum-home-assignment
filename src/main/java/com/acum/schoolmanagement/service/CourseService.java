package com.acum.schoolmanagement.service;

import com.acum.schoolmanagement.dto.CourseSummary;
import com.acum.schoolmanagement.dto.LectureSummary;
import com.acum.schoolmanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<CourseSummary> getCoursesWithStudentCount() {
        List<Object[]> results = courseRepository.findAllWithStudentCountAndLecturesNative();
        Map<Long, CourseSummary> courseSummaryMap = new HashMap<>();

        for (Object[] row : results) {
            Long courseId = ((Number) row[0]).longValue();

            // Fetch or create CourseSummary
            CourseSummary courseSummary = courseSummaryMap.computeIfAbsent(courseId, id -> new CourseSummary(
                    courseId,
                    (String) row[1],               // course_name
                    ((Number) row[2]).intValue(),  // hours
                    ((Number) row[3]).intValue(),  // max_students
                    ((Number) row[4]).intValue()   // student_count
            ));

            // Map lecture details if available
            Long lectureId = (row[5] != null) ? ((Number) row[5]).longValue() : null;
            if (lectureId != null) {
                LectureSummary lectureSummary = new LectureSummary(
                        lectureId,
                        (String) row[6],            // lecturer_name
                        (String) row[7]             // field_of_study
                );
                courseSummary.addLecture(lectureSummary);
            }
        }

        return new ArrayList<>(courseSummaryMap.values());
    }

}
