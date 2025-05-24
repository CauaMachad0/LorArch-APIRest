package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.OcorrenciaDTO;
import com.lorarch.challenge.model.Ocorrencia;
import com.lorarch.challenge.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @GetMapping
    public List<Ocorrencia> listarTodas() {
        return ocorrenciaService.listarTodas();
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