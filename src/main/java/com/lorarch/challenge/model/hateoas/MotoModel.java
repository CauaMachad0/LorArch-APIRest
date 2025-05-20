package com.lorarch.challenge.model.hateoas;

import com.lorarch.challenge.model.StatusMoto;
import org.springframework.hateoas.RepresentationModel;

public class MotoModel extends RepresentationModel<MotoModel> {

    private Long id;
    private String placa;
    private String setor;
    private StatusMoto status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public StatusMoto getStatus() {
        return status;
    }

    public void setStatus(StatusMoto status) {
        this.status = status;
    }
}