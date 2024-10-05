package com.kvy.demogerenciamentoaulas.web.controller;


import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.service.TurmaService;
import com.kvy.demogerenciamentoaulas.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Turma", description = "Contém todas as operações relativas aos recursos de CRUD de Turma.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/turma")
public class TurmaController {

    private final TurmaService turmaService;

    @Operation(summary = "Criar uma nova turma", description = "Recurso para criar uma nova turma",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))) ,
                    @ApiResponse(responseCode = "409", description = "Turma já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Turma> createTurma(@RequestBody Turma turma){
        Turma savedTurma = turmaService.salvar(turma);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTurma);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> getTurmaById(@PathVariable Long id) {
        Turma turma= turmaService.buscarPorId(id);
        return ResponseEntity.ok(turma);
    }

    @GetMapping
    public ResponseEntity<List<Turma>> getTurmaAll(@PathVariable Long id) {
        List<Turma> turmas = turmaService.buscarTodos(id);
        return ResponseEntity.ok(turmas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> updateTurma(@PathVariable Long id, @RequestBody Turma turma) {
        Turma updateTurma = turmaService.editar(id, turma);
        return ResponseEntity.ok(updateTurma);
    }

    @Operation(summary = "Excluir turma", description = "Recurso para excluir uma turma pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Turma excluída com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        turmaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
