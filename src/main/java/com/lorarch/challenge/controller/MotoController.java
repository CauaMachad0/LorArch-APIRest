package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @PostMapping
    public ResponseEntity<Moto> criarMoto(@Valid @RequestBody MotoDTO dto) {
        Moto novaMoto = motoService.criarMoto(dto);
        return ResponseEntity.ok(novaMoto);
    }

    @GetMapping
    public ResponseEntity<List<Moto>> listarTodas() {
        return ResponseEntity.ok(motoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @Valid @RequestBody MotoDTO dto) {
        Moto motoAtualizada = motoService.atualizar(id, dto);
        return ResponseEntity.ok(motoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}