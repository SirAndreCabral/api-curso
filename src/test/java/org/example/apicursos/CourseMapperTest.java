package org.example.apicursos;

import org.example.apicursos.dto.CourseResponseDTO;
import org.example.apicursos.dto.CreateCourseRequestDTO;
import org.example.apicursos.dto.UpdateCourseRequestDTO;
import org.example.apicursos.mapper.CourseMapper;
import org.example.apicursos.model.CourseModel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseMapperTest {

    @Test
    void shouldConvertCreateDTOToModel() {

        CreateCourseRequestDTO requestDTO =
                new CreateCourseRequestDTO(
                        "Java",
                        "Backend"
                );

        CourseModel model =
                CourseMapper.toModel(requestDTO);

        assertEquals("Java", model.getName());
        assertEquals("Backend", model.getCategory());
        assertTrue(model.getActive());
    }


    @Test
    void shouldConvertModelToResponseDTO() {

        UUID id = UUID.randomUUID();

        CourseModel model = CourseModel.builder()
                .id(id)
                .name("Java")
                .category("Backend")
                .active(true)
                .build();

        CourseResponseDTO response =
                CourseMapper.toDTO(model);

        assertEquals(id, response.id());
        assertEquals("Java", response.name());
        assertEquals("Backend", response.category());
        assertTrue(response.active());
    }


    @Test
    void shouldUpdateModelFromDTO() {

        UUID id = UUID.randomUUID();

        CourseModel course = CourseModel.builder()
                .id(id)
                .name("Java")
                .category("Backend")
                .active(true)
                .build();

        UpdateCourseRequestDTO requestDTO =
                new UpdateCourseRequestDTO(
                        "Java Spring Boot",
                        "Programming"
                );

        CourseModel updatedCourse =
                CourseMapper.updateModel(requestDTO, course);

        assertEquals(id, updatedCourse.getId());
        assertEquals("Java Spring Boot", updatedCourse.getName());
        assertEquals("Programming", updatedCourse.getCategory());
        assertTrue(updatedCourse.getActive());
    }
}
