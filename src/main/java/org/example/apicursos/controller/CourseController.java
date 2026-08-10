package org.example.apicursos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.apicursos.dto.CourseResponseDTO;
import org.example.apicursos.dto.CreateCourseRequestDTO;
import org.example.apicursos.dto.UpdateCourseRequestDTO;
import org.example.apicursos.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(("/course"))
@Tag(
        name = "Cursos",
        description = "Operações para gerenciamento de cursos"
)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Operation(
            summary = "Cadastrar curso",
            description = "Cria um novo curso"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/create_course")
    public ResponseEntity<CourseResponseDTO> createCourse(
            @RequestBody @Valid CreateCourseRequestDTO createCourseRequestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.createCourses(createCourseRequestDTO));
    }

    @Operation(
            summary = "Listar cursos",
            description = "Retorna uma lista de todos os cursos cadastrados. Opcionalmente, é possível filtrar por nome e/ou categoria."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso"),
    })
    @GetMapping("/list_courses")
    public ResponseEntity<List<CourseResponseDTO>> listAllCourses(
            @Parameter(description = "Filtra cursos pelo nome")
            @RequestParam(required = false) String name,

            @Parameter(description = "Filtra cursos pela categoria")
            @RequestParam(required = false) String category) {

        return ResponseEntity
                .ok()
                .body(courseService.listCourses(name, category));
    }

    @Operation(
            summary = "Buscar curso por ID",
            description = "Retorna um curso específico a partir do seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de consulta inválidos"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @GetMapping("/list_course/{id}")
    public ResponseEntity<CourseResponseDTO> listOneCourse(
            @Parameter(description = "UUID do curso")
            @PathVariable UUID id) {

        return ResponseEntity.ok().body(courseService.listCourse(id));
    }

    @Operation(
            summary = "Atualizar curso por ID",
            description = "Atualiza os dados de um curso específico a partir do ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID inválido ou dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @PutMapping("/update_course/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @Parameter(description = "UUID do curso")
            @PathVariable UUID id,
            @RequestBody @Valid UpdateCourseRequestDTO updateCourseRequestDTO) {

        return ResponseEntity.ok().body(courseService.updateCourse(id, updateCourseRequestDTO));
    }

    @Operation(
            summary = "Deletar curso por ID",
            description = "Remove um curso do sistema a partir do seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Curso deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID inválido"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @DeleteMapping("/delete_course/{id}")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "UUID do curso")
            @PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Ativar ou desativar um curso por ID",
            description = "Altera o status de ativação de um curso a partir do seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do curso alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID inválido"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    @PatchMapping("/patch/{id}/active")
    public ResponseEntity<CourseResponseDTO> activeCourse(
            @Parameter(description = "UUID do curso")
            @PathVariable UUID id) {
        return ResponseEntity.ok().body(courseService.patchCourse(id));
    }
}
