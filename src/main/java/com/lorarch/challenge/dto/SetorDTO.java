package com.lorarch.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SetorDTO {

    private Long id;

    @NotBlank(message = "O nome do setor é obrigatório")
    @Size(max = 50, message = "O nome do setor deve ter no máximo 50 caracteres")
    private String nome;

    public SetorDTO() {
    }

    public SetorDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}