package com.lorarch.challenge.dto;

import jakarta.validation.constraints.NotBlank;

public class MotoDTO {

    @NotBlank(message = "A placa é obrigatória.")
    private String placa;

    @NotBlank(message = "O status é obrigatório.")
    private String status;

    @NotBlank(message = "O setor é obrigatório.")
    private String setor;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
