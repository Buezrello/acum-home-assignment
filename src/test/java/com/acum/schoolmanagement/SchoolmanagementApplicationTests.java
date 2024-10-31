package com.acum.schoolmanagement;

import com.acum.schoolmanagement.dto.CourseSummary;
import com.acum.schoolmanagement.dto.LectureSummary;
import com.acum.schoolmanagement.repository.CourseRepository;
import com.acum.schoolmanagement.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SchoolmanagementApplicationTests {

	@Autowired
	private CourseService courseService;

	@MockBean
	private CourseRepository courseRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetCoursesWithStudentCount() {
		// Arrange: Set up mock data as returned by native query
		List<Object[]> mockResults = Arrays.asList(
				new Object[]{1L, "Calculus", 40, 30, 10, 100L, "Dr. Alice Green", "Mathematics"},
				new Object[]{1L, "Calculus", 40, 30, 10, 101L, "Dr. Bob White", "Mathematics"},
				new Object[]{2L, "Algorithms", 30, 25, 8, null, null, null}
		);

		when(courseRepository.findAllWithStudentCountAndLecturesNative()).thenReturn(mockResults);

		// Act: Call the method under test
		List<CourseSummary> courseSummaries = courseService.getCoursesWithStudentCount();

		// Assert: Verify the results
		assertEquals(2, courseSummaries.size());

		// Check the first course summary (Calculus)
		CourseSummary calculusSummary = courseSummaries.stream()
				.filter(course -> course.getCourseName().equals("Calculus"))
				.findFirst()
				.orElse(null);
		assertNotNull(calculusSummary);
		assertEquals(10, calculusSummary.getStudentCount());
		assertEquals(2, calculusSummary.getLectures().size());

		// Check the lecture details
		LectureSummary lecture1 = calculusSummary.getLectures().get(0);
		assertEquals(100L, lecture1.getId());
		assertEquals("Dr. Alice Green", lecture1.getLecturerName());
		assertEquals("Mathematics", lecture1.getFieldOfStudy());

		LectureSummary lecture2 = calculusSummary.getLectures().get(1);
		assertEquals(101L, lecture2.getId());
		assertEquals("Dr. Bob White", lecture2.getLecturerName());
		assertEquals("Mathematics", lecture2.getFieldOfStudy());

		// Check the second course summary (Algorithms)
		CourseSummary algorithmsSummary = courseSummaries.stream()
				.filter(course -> course.getCourseName().equals("Algorithms"))
				.findFirst()
				.orElse(null);
		assertNotNull(algorithmsSummary);
		assertEquals(8, algorithmsSummary.getStudentCount());
		assertEquals(0, algorithmsSummary.getLectures().size());  // No lectures associated with Algorithms
	}

	@Test
	void testGetCoursesWithStudentCount_noCoursesAvailable() {
		// Arrange: Mock empty results
		when(courseRepository.findAllWithStudentCountAndLecturesNative()).thenReturn(Collections.emptyList());

		// Act
		List<CourseSummary> courseSummaries = courseService.getCoursesWithStudentCount();

		// Assert: Verify no courses are returned
		assertTrue(courseSummaries.isEmpty(), "Course list should be empty when no data is available");
	}

	@Test
	void testGetCoursesWithStudentCount_coursesWithNoStudentsOrLectures() {
		// Arrange: Mock data for courses without students or lectures
		List<Object[]> mockResults = Arrays.asList(
				new Object[]{1L, "Mathematics", 40, 30, 0, null, null, null},
				new Object[]{2L, "Biology Basics", 35, 20, 0, null, null, null}
		);

		when(courseRepository.findAllWithStudentCountAndLecturesNative()).thenReturn(mockResults);

		// Act
		List<CourseSummary> courseSummaries = courseService.getCoursesWithStudentCount();

		// Assert
		assertEquals(2, courseSummaries.size());

		// Verify "Mathematics" course with zero students and lectures
		CourseSummary mathSummary = courseSummaries.stream()
				.filter(course -> course.getCourseName().equals("Mathematics"))
				.findFirst()
				.orElse(null);
		assertNotNull(mathSummary);
		assertEquals(0, mathSummary.getStudentCount());
		assertTrue(mathSummary.getLectures().isEmpty(), "Mathematics should have no lectures");

		// Verify "Biology Basics" course with zero students and lectures
		CourseSummary biologySummary = courseSummaries.stream()
				.filter(course -> course.getCourseName().equals("Biology Basics"))
				.findFirst()
				.orElse(null);
		assertNotNull(biologySummary);
		assertEquals(0, biologySummary.getStudentCount());
		assertTrue(biologySummary.getLectures().isEmpty(), "Biology Basics should have no lectures");
	}

	@Test
	void testGetCoursesWithStudentCount_multipleCoursesWithLectures() {
		// Arrange: Mock data for courses with multiple lectures
		List<Object[]> mockResults = Arrays.asList(
				new Object[]{1L, "Calculus", 40, 30, 15, 100L, "Dr. Alice Green", "Mathematics"},
				new Object[]{1L, "Calculus", 40, 30, 15, 101L, "Dr. Bob White", "Mathematics"},
				new Object[]{2L, "Physics 101", 50, 35, 10, 102L, "Dr. Charles Grey", "Physics"},
				new Object[]{2L, "Physics 101", 50, 35, 10, 103L, "Dr. Diana Blue", "Physics"}
		);

		when(courseRepository.findAllWithStudentCountAndLecturesNative()).thenReturn(mockResults);

		// Act
		List<CourseSummary> courseSummaries = courseService.getCoursesWithStudentCount();

		// Assert
		assertEquals(2, courseSummaries.size());

		// Verify "Calculus" course with two lectures
		CourseSummary calculusSummary = courseSummaries.stream()
				.filter(course -> course.getCourseName().equals("Calculus"))
				.findFirst()
				.orElse(null);
		assertNotNull(calculusSummary);
		assertEquals(15, calculusSummary.getStudentCount());
		assertEquals(2, calculusSummary.getLectures().size());

		// Verify lecture details for "Calculus"
		LectureSummary lecture1 = calculusSummary.getLectures().get(0);
		assertEquals(100L, lecture1.getId());
		assertEquals("Dr. Alice Green", lecture1.getLecturerName());
		assertEquals("Mathematics", lecture1.getFieldOfStudy());

		LectureSummary lecture2 = calculusSummary.getLectures().get(1);
		assertEquals(101L, lecture2.getId());
		assertEquals("Dr. Bob White", lecture2.getLecturerName());
		assertEquals("Mathematics", lecture2.getFieldOfStudy());

		// Verify "Physics 101" course with two lectures
		CourseSummary physicsSummary = courseSummaries.stream()
				.filter(course -> course.getCourseName().equals("Physics 101"))
				.findFirst()
				.orElse(null);
		assertNotNull(physicsSummary);
		assertEquals(10, physicsSummary.getStudentCount());
		assertEquals(2, physicsSummary.getLectures().size());

		// Verify lecture details for "Physics 101"
		LectureSummary lecture3 = physicsSummary.getLectures().get(0);
		assertEquals(102L, lecture3.getId());
		assertEquals("Dr. Charles Grey", lecture3.getLecturerName());
		assertEquals("Physics", lecture3.getFieldOfStudy());

		LectureSummary lecture4 = physicsSummary.getLectures().get(1);
		assertEquals(103L, lecture4.getId());
		assertEquals("Dr. Diana Blue", lecture4.getLecturerName());
		assertEquals("Physics", lecture4.getFieldOfStudy());
	}

}
