package com.lorarch.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MotoDTO {

    @NotBlank(message = "A placa é obrigatória.")
    @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$",
            message = "A placa deve estar no formato válido (ex: ABC1D23 ou ABC1234).")
    private String placa;

    @NotBlank(message = "O modelo é obrigatório.")
    @Size(min = 2, max = 50, message = "O modelo deve ter entre 2 e 50 caracteres.")
    private String modelo;

    @NotBlank(message = "O status é obrigatório.")
    private String status;

    @NotBlank(message = "O setor é obrigatório.")
    @Size(min = 2, max = 50, message = "O setor deve ter entre 2 e 50 caracteres.")
    private String setor;

    public MotoDTO() {}

    public MotoDTO(String placa, String modelo, String status, String setor) {
        this.placa = placa;
        this.modelo = modelo;
        this.status = status;
        this.setor = setor;
    }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
}
