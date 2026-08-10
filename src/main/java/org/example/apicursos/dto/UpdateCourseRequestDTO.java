package org.example.apicursos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UpdateCourseRequestDTO(

        @Size(
                min = 3,
                max = 50,
                message = "O nome deve conter entre 3 a 50 caracteres."
        )
        @Schema(
                description = "Novo nome do curso",
                example = "PHP Laravel"
        )
        String name,

        @Size(
                min = 3,
                max = 50,
                message = "A categoria deve conter entre 3 a 50 caracteres."
        )
        @Schema(
                description = "Nova categoria do curso.",
                example = "Backend"
        )
        String category
) {}
