package org.example.apicursos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record CourseResponseDTO(
        @Schema(
                description = "UUID do curso.",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "Nome do curso.",
                example = "Java"
        )
        String name,

        @Schema(
                description = "Categoria do curso.",
                example = "Backend"
        )
        String category,

        @Schema(
                description = "Indica se o curso está ativo.",
                example = "true"
        )
        Boolean active,

        @Schema(
                description = "Data de criação do curso.",
                example = "2026-03-23T14:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Data da última atualização do curso.",
                example = "2026-05-23T10:15:30"
        )
        LocalDateTime updatedAt
) {}
