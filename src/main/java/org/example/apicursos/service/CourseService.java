package org.example.apicursos.service;

import org.example.apicursos.dto.CourseResponseDTO;
import org.example.apicursos.dto.CreateCourseRequestDTO;
import org.example.apicursos.dto.UpdateCourseRequestDTO;
import org.example.apicursos.exception.CourseNotFoundException;
import org.example.apicursos.mapper.CourseMapper;
import org.example.apicursos.model.CourseModel;
import org.example.apicursos.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    private CourseModel findCourseById(UUID id) {
        return this.courseRepository
                .findById(id)
                .orElseThrow(CourseNotFoundException::new);
    }

    private List<CourseModel> findCourses(String name, String category) {

        if (name != null && category != null) {
            return this.courseRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);

        } else if (name != null) {
            return this.courseRepository.findByNameContainingIgnoreCase(name);

        } else if (category != null) {
            return this.courseRepository.findByCategoryContainingIgnoreCase(category);

        } else {
            return this.courseRepository.findAll();
        }
    }

    public CourseResponseDTO createCourses(CreateCourseRequestDTO createCourseRequestDTO) {

        CourseModel newCourse = CourseMapper.toModel(createCourseRequestDTO);

        CourseModel savedCourse = courseRepository.save(newCourse);

        return CourseMapper.toDTO(savedCourse);
    }

    public List<CourseResponseDTO> listCourses(String name, String category) {

        return findCourses(name, category)
                .stream()
                .map(CourseMapper::toDTO)
                .toList();
    }

    public CourseResponseDTO listCourse(UUID id) {

        return CourseMapper.toDTO(findCourseById(id));
    }

    public CourseResponseDTO updateCourse(UUID id, UpdateCourseRequestDTO updateCourseRequestDTO) {

        CourseModel course = findCourseById(id);

        CourseModel updatedCourse = CourseMapper.updateModel(updateCourseRequestDTO, course);

        updatedCourse = courseRepository.save(updatedCourse);

        return CourseMapper.toDTO(updatedCourse);
    }

    public void deleteCourse(UUID id) {

        CourseModel course = findCourseById(id);

        courseRepository.deleteById(course.getId());

    }

    public CourseResponseDTO patchCourse(UUID id) {

        CourseModel course = findCourseById(id);

        Boolean valorInvertido = !course.getActive();

        course.setActive(valorInvertido);

        CourseModel saved = courseRepository.save(course);

        return CourseMapper.toDTO(saved);
    }
}
