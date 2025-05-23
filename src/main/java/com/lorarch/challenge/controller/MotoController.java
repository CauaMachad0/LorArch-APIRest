package com.lorarch.challenge.controller;

import com.lorarch.challenge.assembler.MotoModelAssembler;
import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @Autowired
    private MotoModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Moto>> criarMoto(@Valid @RequestBody MotoDTO dto) {
        Moto novaMoto = motoService.criarMoto(dto);
        EntityModel<Moto> entityModel = assembler.toModel(novaMoto);
        return ResponseEntity.created(
                linkTo(methodOn(MotoController.class).buscarPorId(novaMoto.getId())).toUri()
        ).body(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Moto>>> listarTodas() {
        List<EntityModel<Moto>> motos = motoService.listarTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(motos,
                        linkTo(methodOn(MotoController.class).listarTodas()).withSelfRel()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Moto>> buscarPorId(@PathVariable Long id) {
        Moto moto = motoService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(moto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Moto>> atualizar(@PathVariable Long id, @Valid @RequestBody MotoDTO dto) {
        Moto motoAtualizada = motoService.atualizar(id, dto);
        return ResponseEntity.ok(assembler.toModel(motoAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
