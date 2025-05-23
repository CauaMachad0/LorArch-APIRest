package com.lorarch.challenge.assembler;

import com.lorarch.challenge.controller.MotoController;
import com.lorarch.challenge.model.Moto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MotoModelAssembler implements RepresentationModelAssembler<Moto, EntityModel<Moto>> {

    @Override
    public EntityModel<Moto> toModel(Moto moto) {
        return EntityModel.of(moto,
                linkTo(methodOn(MotoController.class).buscarPorId(moto.getId())).withSelfRel(),
                linkTo(methodOn(MotoController.class).listarTodas()).withRel("motos")
        );
    }
}
