package org.example.apicursos;

import org.example.apicursos.model.CourseModel;
import org.example.apicursos.repository.CourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
class CourseIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("api_cursos_test")
                    .withUsername("test")
                    .withPassword("test");

    @Autowired
    private CourseRepository courseRepository;

    @AfterEach
    void cleanDatabase() {
        courseRepository.deleteAll();
    }

    @Test
    void shouldSaveCourseInDatabase() {

        CourseModel course = CourseModel.builder()
                .name("Java")
                .category("Backend")
                .active(true)
                .build();

        CourseModel savedCourse =
                courseRepository.save(course);

        assertNotNull(savedCourse.getId());
        assertEquals("Java", savedCourse.getName());
        assertEquals("Backend", savedCourse.getCategory());
        assertTrue(savedCourse.getActive());
    }

    @Test
    void shouldFindCourseByName() {

        CourseModel course = CourseModel.builder()
                .name("Spring Boot")
                .category("Backend")
                .active(true)
                .build();

        courseRepository.save(course);

        List<CourseModel> courses =
                courseRepository.findByNameContainingIgnoreCase("Spring");

        assertEquals(1, courses.size());
        assertEquals("Spring Boot", courses.get(0).getName());
    }

    @Test
    void shouldFindCourseByCategory() {

        CourseModel course = CourseModel.builder()
                .name("React")
                .category("Frontend")
                .active(true)
                .build();

        courseRepository.save(course);

        List<CourseModel> courses =
                courseRepository.findByCategoryContainingIgnoreCase("Front");

        assertEquals(1, courses.size());
        assertEquals("Frontend", courses.get(0).getCategory());
    }

    @Test
    void shouldFindCourseByNameAndCategory() {

        CourseModel course = CourseModel.builder()
                .name("Java Spring")
                .category("Backend")
                .active(true)
                .build();

        courseRepository.save(course);

        List<CourseModel> courses =
                courseRepository
                        .findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(
                                "Java",
                                "Backend"
                        );

        assertEquals(1, courses.size());
        assertEquals("Java Spring", courses.get(0).getName());
        assertEquals("Backend", courses.get(0).getCategory());
    }
}
