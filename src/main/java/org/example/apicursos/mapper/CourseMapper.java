package org.example.apicursos.mapper;

import org.example.apicursos.dto.CourseResponseDTO;
import org.example.apicursos.dto.CreateCourseRequestDTO;
import org.example.apicursos.dto.UpdateCourseRequestDTO;
import org.example.apicursos.model.CourseModel;

public class CourseMapper {

    public static CourseResponseDTO toDTO(CourseModel courseModel) {
        return new CourseResponseDTO(
                courseModel.getId(),
                courseModel.getName(),
                courseModel.getCategory(),
                courseModel.getActive(),
                courseModel.getCreatedAt(),
                courseModel.getUpdatedAt()
        );
    }

    public static CourseModel toModel(CreateCourseRequestDTO dto) {
        return CourseModel.builder()
                .name(dto.name())
                .category(dto.category())
                .build();

    }

    public static CourseModel updateModel(UpdateCourseRequestDTO dto, CourseModel model) {
        if (dto.name() != null) {
            model.setName(dto.name());
        }

        if (dto.category() != null) {
            model.setCategory(dto.category());
        }

        return model;
    }
}
