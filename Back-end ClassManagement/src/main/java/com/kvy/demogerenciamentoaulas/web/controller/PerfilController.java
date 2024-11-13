package com.kvy.demogerenciamentoaulas.web.controller;

import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.service.PerfilService;
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

@Tag(name = "Perfis", description = "Contém todas as operações relativas aos recursos de CRUD de perfil.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    @Operation(summary = "Criar um novo perfil", description = "Recurso para criar um novo perfil",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Perfil.class))),
                    @ApiResponse(responseCode = "409", description = "Perfil já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Perfil> createPerfil(@RequestBody Perfil perfil) {
        Perfil savedPerfil = perfilService.salvar(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfil);
    }

    @Operation(summary = "Recuperar um perfil pelo id", description = "Recuperar um perfil pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Perfil.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Perfil> getPerfilById(@PathVariable Long id) {
        Perfil perfil = perfilService.buscarPorId(id);
        return ResponseEntity.ok(perfil);
    }

    @Operation(summary = "Atualizar perfil", description = "Recurso para atualizar um perfil pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Perfil.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Perfil> updatePerfil(@PathVariable Long id, @RequestBody Perfil perfil) {
        Perfil updatedPerfil = perfilService.editar(id, perfil);
        return ResponseEntity.ok(updatedPerfil);
    }

    @Operation(summary = "Excluir perfil", description = "Recurso para excluir um perfil pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Perfil excluído com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        perfilService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os perfis", description = "Recuperar todos os perfis",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recursos recuperados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Perfil.class)))
            })
    @GetMapping
    public ResponseEntity<List<Perfil>> getPerfilAll() {
        List<Perfil> perfis = perfilService.buscarTodos();
        return ResponseEntity.ok(perfis);
    }
}