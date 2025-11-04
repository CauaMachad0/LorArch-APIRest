package com.lorarch.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CH_SETOR", schema = "DBO")
@SequenceGenerator(name = "seq_setor", sequenceName = "SEQ_SETOR", allocationSize = 1, initialValue = 1, schema = "DBO")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_setor")
    @Column(name = "ID_SETOR")
    private Long id;

    @NotBlank @Size(max = 50)
    @Column(name = "NOME_SETOR", nullable = false, length = 50)
    private String nome;

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
