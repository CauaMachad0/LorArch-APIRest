package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.SetorDTO;
import com.lorarch.challenge.service.SetorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setores")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @GetMapping
    public ResponseEntity<List<SetorDTO>> listarTodos() {
        List<SetorDTO> setores = setorService.listarTodos();
        return ResponseEntity.ok(setores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(@PathVariable Long id) {
        SetorDTO setor = setorService.buscarPorId(id);
        return ResponseEntity.ok(setor);
    }

    @PostMapping
    public ResponseEntity<SetorDTO> criar(@Valid @RequestBody SetorDTO setorDTO) {
        SetorDTO novoSetor = setorService.criar(setorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoSetor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody SetorDTO setorDTO) {
        SetorDTO setorAtualizado = setorService.atualizar(id, setorDTO);
        return ResponseEntity.ok(setorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        setorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}