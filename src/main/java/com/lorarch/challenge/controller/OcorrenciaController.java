package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.OcorrenciaDTO;
import com.lorarch.challenge.model.Ocorrencia;
import com.lorarch.challenge.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Ocorrencia> criar(@RequestBody OcorrenciaDTO dto) {
        Ocorrencia ocorrencia = ocorrenciaService.criarOcorrencia(dto);
        return new ResponseEntity<>(ocorrencia, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizar(@PathVariable Long id, @RequestBody OcorrenciaDTO dto) {
        Ocorrencia ocorrenciaAtualizada = ocorrenciaService.atualizarOcorrencia(id, dto);
        return ResponseEntity.ok(ocorrenciaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ocorrenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
