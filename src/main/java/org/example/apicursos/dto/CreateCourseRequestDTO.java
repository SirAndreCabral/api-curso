package org.example.apicursos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCourseRequestDTO(
        @NotBlank(message = "O nome do curso é obrigatório.")
        @Schema(
                description = "Nome do curso a ser cadastrado.",
                example = "Java Spring Boot"
        )
        String name,

        @NotBlank(message = "A categoria do curso é obrigatório.")
        @Schema(
                description = "Categoria do Curso",
                example = "Programação"
        )
        String category
) {
}
