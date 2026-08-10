package org.example.apicursos;

import org.example.apicursos.dto.CourseResponseDTO;
import org.example.apicursos.dto.CreateCourseRequestDTO;
import org.example.apicursos.dto.UpdateCourseRequestDTO;
import org.example.apicursos.exception.CourseNotFoundException;
import org.example.apicursos.model.CourseModel;
import org.example.apicursos.repository.CourseRepository;
import org.example.apicursos.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldCreateCourseSuccessfully() {
        CreateCourseRequestDTO requestDTO = new CreateCourseRequestDTO("Java", "Backend");
        CourseModel savedCourse = CourseModel.builder().id(UUID.randomUUID()).name("Java").category("Backend").active(true).build();
        when(courseRepository.save(any(CourseModel.class))).thenReturn(savedCourse);
        CourseResponseDTO response = courseService.createCourses(requestDTO);
        assertEquals("Java", response.name());
        assertEquals("Backend", response.category());
        assertTrue(response.active());
        verify(courseRepository).save(any(CourseModel.class));
    }

    @Test
    void shouldListAllCoursesWithoutFilters() {
        CourseModel course1 = CourseModel.builder().id(UUID.randomUUID()).name("Java").category("Backend").active(true).build();
        CourseModel course2 = CourseModel.builder().id(UUID.randomUUID()).name("React").category("Frontend").active(true).build();
        List<CourseModel> courses = List.of(course1, course2);
        when(courseRepository.findAll()).thenReturn(courses);
        List<CourseResponseDTO> response = courseService.listCourses(null, null);
        assertEquals(2, response.size());
        assertEquals("Java", response.get(0).name());
        assertEquals("Backend", response.get(0).category());
        assertEquals("React", response.get(1).name());
        assertEquals("Frontend", response.get(1).category());
        verify(courseRepository).findAll();
    }

    @Test
    void shouldListCoursesByName() {
        CourseModel course1 = CourseModel.builder().id(UUID.randomUUID()).name("Java").category("Backend").active(true).build();
        CourseModel course2 = CourseModel.builder().id(UUID.randomUUID()).name("Java Spring").category("Backend").active(true).build();
        List<CourseModel> courses = List.of(course1, course2);
        when(courseRepository.findByNameContainingIgnoreCase("Java")).thenReturn(courses);
        List<CourseResponseDTO> response = courseService.listCourses("Java", null);
        assertEquals(2, response.size());
        assertEquals("Java", response.get(0).name());
        assertEquals("Java Spring", response.get(1).name());
        verify(courseRepository).findByNameContainingIgnoreCase("Java");
    }

    @Test
    void shouldListCoursesByCategory() {
        CourseModel course1 = CourseModel.builder().id(UUID.randomUUID()).name("Java").category("Backend").active(true).build();
        CourseModel course2 = CourseModel.builder().id(UUID.randomUUID()).name("Spring Boot").category("Backend").active(true).build();
        List<CourseModel> courses = List.of(course1, course2);
        when(courseRepository.findByCategoryContainingIgnoreCase("Backend")).thenReturn(courses);
        List<CourseResponseDTO> response = courseService.listCourses(null, "Backend");
        assertEquals(2, response.size());
        assertEquals("Backend", response.get(0).category());
        assertEquals("Backend", response.get(1).category());
        verify(courseRepository).findByCategoryContainingIgnoreCase("Backend");
    }

    @Test
    void shouldListCoursesByNameAndCategory() {
        CourseModel course = CourseModel.builder().id(UUID.randomUUID()).name("React").category("Frontend").active(true).build();
        List<CourseModel> courses = List.of(course);
        when(courseRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase("React", "Frontend")).thenReturn(courses);
        List<CourseResponseDTO> response = courseService.listCourses("React", "Frontend");
        assertEquals(1, response.size());
        assertEquals("React", response.get(0).name());
        assertEquals("Frontend", response.get(0).category());
        verify(courseRepository).findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase("React", "Frontend");
    }

    @Test
    void shouldListCourseById() {
        UUID uuid = UUID.randomUUID();
        CourseModel course = CourseModel.builder().id(uuid).name("Java").category("Backend").active(true).build();
        when(courseRepository.findById(uuid)).thenReturn(Optional.of(course));
        CourseResponseDTO response = courseService.listCourse(uuid);
        assertEquals(uuid, response.id());
        assertEquals("Java", response.name());
        assertEquals("Backend", response.category());
        assertTrue(response.active());
        verify(courseRepository).findById(uuid);
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFound() {
        UUID uuid = UUID.randomUUID();
        when(courseRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.listCourse(uuid));
        verify(courseRepository).findById(uuid);
    }

    @Test
    void shouldUpdateCourse() {
        UUID uuid = UUID.randomUUID();
        UpdateCourseRequestDTO requestDTO = new UpdateCourseRequestDTO("Java Spring", "Backend");
        CourseModel course = CourseModel.builder().id(uuid).name("Java").category("Backend").active(true).build();
        when(courseRepository.findById(uuid)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(CourseModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CourseResponseDTO response = courseService.updateCourse(uuid, requestDTO);
        assertEquals(uuid, response.id());
        assertEquals("Java Spring", response.name());
        assertEquals("Backend", response.category());
        assertTrue(response.active());
        verify(courseRepository).findById(uuid);
        verify(courseRepository).save(any(CourseModel.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCourse() {
        UUID uuid = UUID.randomUUID();
        UpdateCourseRequestDTO requestDTO = new UpdateCourseRequestDTO("Java Spring", "Backend");
        when(courseRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(uuid, requestDTO));
        verify(courseRepository).findById(uuid);
    }

    @Test
    void shouldDeleteCourse() {
        UUID uuid = UUID.randomUUID();
        CourseModel course = CourseModel.builder().id(uuid).name("Java").category("Backend").active(true).build();
        when(courseRepository.findById(uuid)).thenReturn(Optional.of(course));
        courseService.deleteCourse(uuid);
        verify(courseRepository).findById(uuid);
        verify(courseRepository).deleteById(uuid);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingCourse() {
        UUID uuid = UUID.randomUUID();
        when(courseRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(uuid));
        verify(courseRepository).findById(uuid);
    }

    @Test
    void shouldDeactivateCourse() {
        UUID uuid = UUID.randomUUID();
        CourseModel course = CourseModel.builder().id(uuid).name("Java").category("Backend").active(true).build();
        when(courseRepository.findById(uuid)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(CourseModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CourseResponseDTO response = courseService.patchCourse(uuid);
        assertFalse(response.active());
        verify(courseRepository).findById(uuid);
        verify(courseRepository).save(any(CourseModel.class));
    }

    @Test
    void shouldActivateCourse() {
        UUID uuid = UUID.randomUUID();
        CourseModel course = CourseModel.builder().id(uuid).name("Java").category("Backend").active(false).build();
        when(courseRepository.findById(uuid)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(CourseModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CourseResponseDTO response = courseService.patchCourse(uuid);
        assertTrue(response.active());
        verify(courseRepository).findById(uuid);
        verify(courseRepository).save(any(CourseModel.class));
    }

    @Test
    void shouldThrowExceptionWhenPatchingNonExistingCourse() {
        UUID uuid = UUID.randomUUID();
        when(courseRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> courseService.patchCourse(uuid));
        verify(courseRepository).findById(uuid);
    }
}