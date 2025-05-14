package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public List<Moto> listarTodas() {
        return motoService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Moto> criarMoto(@RequestBody MotoDTO dto) {
        Moto moto = motoService.criarMoto(dto);
        return new ResponseEntity<>(moto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody Moto motoAtualizada) {
        return ResponseEntity.ok(motoService.atualizar(id, motoAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
