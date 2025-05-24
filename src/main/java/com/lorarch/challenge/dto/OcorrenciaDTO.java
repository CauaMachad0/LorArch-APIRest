package com.lorarch.challenge.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class OcorrenciaDTO {

    @NotBlank(message = "O tipo de ocorrência é obrigatório.")
    @Size(min = 3, max = 30, message = "O tipo deve ter entre 3 e 30 caracteres.")
    private String tipo;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 5, max = 200, message = "A descrição deve ter entre 5 e 200 caracteres.")
    private String descricao;

    @NotNull(message = "A data é obrigatória.")
    @FutureOrPresent(message = "A data não pode estar no passado.")
    private LocalDate data;

    @NotNull(message = "O ID da moto é obrigatório.")
    private Long motoId;

    public OcorrenciaDTO() {}

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
}