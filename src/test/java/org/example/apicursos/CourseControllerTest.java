package org.example.apicursos;

import org.example.apicursos.controller.CourseController;
import org.example.apicursos.dto.CourseResponseDTO;
import org.example.apicursos.dto.CreateCourseRequestDTO;
import org.example.apicursos.dto.UpdateCourseRequestDTO;
import org.example.apicursos.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    @Test
    void shouldCreateCourse() throws Exception {

        UUID id = UUID.randomUUID();

        CourseResponseDTO response = new CourseResponseDTO(
                id,
                "Java",
                "Backend",
                true,
                null,
                null
        );

        when(courseService.createCourses(any(CreateCourseRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/course/create_course")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "Java",
                                    "category": "Backend"
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.category").value("Backend"))
                .andExpect(jsonPath("$.active").value(true));

        verify(courseService)
                .createCourses(any(CreateCourseRequestDTO.class));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingCourseWithInvalidData()
            throws Exception {

        mockMvc.perform(
                        post("/course/create_course")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "",
                                    "category": ""
                                }
                                """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldListAllCourses() throws Exception {

        CourseResponseDTO course1 = new CourseResponseDTO(
                UUID.randomUUID(),
                "Java",
                "Backend",
                true,
                null,
                null
        );

        CourseResponseDTO course2 = new CourseResponseDTO(
                UUID.randomUUID(),
                "React",
                "Frontend",
                true,
                null,
                null
        );

        when(courseService.listCourses(null, null))
                .thenReturn(List.of(course1, course2));

        mockMvc.perform(
                        get("/course/list_courses")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[0].category").value("Backend"))
                .andExpect(jsonPath("$[1].name").value("React"))
                .andExpect(jsonPath("$[1].category").value("Frontend"));

        verify(courseService)
                .listCourses(null, null);
    }

    @Test
    void shouldListCoursesByName() throws Exception {

        CourseResponseDTO course = new CourseResponseDTO(
                UUID.randomUUID(),
                "Java",
                "Backend",
                true,
                null,
                null
        );

        when(courseService.listCourses("Java", null))
                .thenReturn(List.of(course));

        mockMvc.perform(
                        get("/course/list_courses")
                                .param("name", "Java")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Java"));

        verify(courseService)
                .listCourses("Java", null);
    }

    @Test
    void shouldListCoursesByCategory() throws Exception {

        CourseResponseDTO course = new CourseResponseDTO(
                UUID.randomUUID(),
                "Java",
                "Backend",
                true,
                null,
                null
        );

        when(courseService.listCourses(null, "Backend"))
                .thenReturn(List.of(course));

        mockMvc.perform(
                        get("/course/list_courses")
                                .param("category", "Backend")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].category").value("Backend"));

        verify(courseService)
                .listCourses(null, "Backend");
    }

    @Test
    void shouldListCoursesByNameAndCategory() throws Exception {

        CourseResponseDTO course = new CourseResponseDTO(
                UUID.randomUUID(),
                "React",
                "Frontend",
                true,
                null,
                null
        );

        when(courseService.listCourses("React", "Frontend"))
                .thenReturn(List.of(course));

        mockMvc.perform(
                        get("/course/list_courses")
                                .param("name", "React")
                                .param("category", "Frontend")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("React"))
                .andExpect(jsonPath("$[0].category").value("Frontend"));

        verify(courseService)
                .listCourses("React", "Frontend");
    }

    @Test
    void shouldListCourseById() throws Exception {

        UUID id = UUID.randomUUID();

        CourseResponseDTO response = new CourseResponseDTO(
                id,
                "Java",
                "Backend",
                true,
                null,
                null
        );

        when(courseService.listCourse(id))
                .thenReturn(response);

        mockMvc.perform(
                        get("/course/list_course/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.category").value("Backend"))
                .andExpect(jsonPath("$.active").value(true));

        verify(courseService)
                .listCourse(id);
    }

    @Test
    void shouldReturnBadRequestWhenIdIsInvalid() throws Exception {

        mockMvc.perform(
                        get("/course/list_course/{id}", "uuid-invalido")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateCourse() throws Exception {

        UUID id = UUID.randomUUID();

        CourseResponseDTO response = new CourseResponseDTO(
                id,
                "Java Spring",
                "Backend",
                true,
                null,
                null
        );

        when(courseService.updateCourse(
                any(UUID.class),
                any(UpdateCourseRequestDTO.class)
        )).thenReturn(response);

        mockMvc.perform(
                        put("/course/update_course/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "Java Spring",
                                    "category": "Backend"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java Spring"))
                .andExpect(jsonPath("$.category").value("Backend"));

        verify(courseService)
                .updateCourse(
                        any(UUID.class),
                        any(UpdateCourseRequestDTO.class)
                );
    }

    @Test
    void shouldReturnBadRequestWhenUpdatingWithInvalidData()
            throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(
                        put("/course/update_course/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "A",
                                    "category": "B"
                                }
                                """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenUpdatingWithInvalidId()
            throws Exception {

        mockMvc.perform(
                        put("/course/update_course/{id}", "uuid-invalido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "name": "Java Spring",
                                    "category": "Backend"
                                }
                                """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteCourse() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(
                        delete("/course/delete_course/{id}", id)
                )
                .andExpect(status().isNoContent());

        verify(courseService)
                .deleteCourse(id);
    }

    @Test
    void shouldReturnBadRequestWhenDeletingWithInvalidId()
            throws Exception {

        mockMvc.perform(
                        delete("/course/delete_course/{id}", "uuid-invalido")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldPatchCourseStatus() throws Exception {

        UUID id = UUID.randomUUID();

        CourseResponseDTO response = new CourseResponseDTO(
                id,
                "Java",
                "Backend",
                false,
                null,
                null
        );

        when(courseService.patchCourse(id))
                .thenReturn(response);

        mockMvc.perform(
                        patch("/course/patch/{id}/active", id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.active").value(false));

        verify(courseService)
                .patchCourse(id);
    }

    @Test
    void shouldReturnBadRequestWhenPatchingWithInvalidId()
            throws Exception {

        mockMvc.perform(
                        patch("/course/patch/{id}/active", "uuid-invalido")
                )
                .andExpect(status().isBadRequest());
    }
}
