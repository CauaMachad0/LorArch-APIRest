package com.lorarch.challenge.dto;

import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.Setor;
import com.lorarch.challenge.model.StatusMoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MotoDTO {

    private Long id;

    @NotBlank(message = "{moto.placa.notblank}")
    @Size(max = 10, message = "{moto.placa.size}")
    private String placa;

    @NotBlank(message = "{moto.modelo.notblank}")
    @Size(max = 50, message = "{moto.modelo.size}")
    private String modelo;

    @NotBlank(message = "{moto.status.notnull}")
    private String status;

    @NotNull(message = "{moto.setor.notnull}")
    private Long setor;

    public MotoDTO() {
    }

    public MotoDTO(Moto moto) {
        this.id = moto.getId();
        this.placa = moto.getPlaca();
        this.modelo = moto.getModelo();
        this.status = moto.getStatus() != null ? moto.getStatus().name() : StatusMoto.DISPONIVEL.name();
        this.setor = moto.getSetor();
    }


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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}
