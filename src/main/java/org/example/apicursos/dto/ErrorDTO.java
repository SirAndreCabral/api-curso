package org.example.apicursos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorDTO(

        @Schema(
                description = "Código de status HTTP da resposta.",
                example = "404"
        )
        int status,

        @Schema(
                description = "Mensagem descritiva do erro.",
                example = "Curso não encontrado."
        )
        String message

) {}
