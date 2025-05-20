package com.lorarch.challenge.assembler;

import com.lorarch.challenge.controller.MotoController;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.hateoas.MotoModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MotoModelAssembler extends RepresentationModelAssemblerSupport<Moto, MotoModel> {

    public MotoModelAssembler() {
        super(MotoController.class, MotoModel.class);
    }

    @Override
    public MotoModel toModel(Moto moto) {
        MotoModel model = new MotoModel();
        model.setId(moto.getId());
        model.setPlaca(moto.getPlaca());
        model.setSetor(moto.getSetor());
        model.setStatus(moto.getStatus());

        model.add(linkTo(methodOn(MotoController.class).buscarPorId(moto.getId())).withSelfRel());
        model.add(linkTo(methodOn(MotoController.class).listarTodas()).withRel("todas-as-motos"));

        return model;
    }
}