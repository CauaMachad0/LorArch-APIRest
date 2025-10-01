package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.OcorrenciaDTO;
import com.lorarch.challenge.model.Ocorrencia;
import com.lorarch.challenge.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @GetMapping("/paginado")
    public ResponseEntity<Page<Ocorrencia>> listarPaginado(
            @PageableDefault(size = 5, sort = "data") Pageable pageable) {
        return ResponseEntity.ok(ocorrenciaService.listarPaginado(pageable));
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(ocorrenciaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ocorrenciaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Ocorrencia> criar(@Valid @RequestBody OcorrenciaDTO dto) {
        Ocorrencia nova = ocorrenciaService.criar(dto);
        return ResponseEntity.created(URI.create("/api/ocorrencias/" + nova.getId())).body(nova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody OcorrenciaDTO dto) {
        return ResponseEntity.ok(ocorrenciaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ocorrenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
