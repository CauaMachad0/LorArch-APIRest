package com.lorarch.challenge.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDate dataRegistro;

    @ManyToOne
    @JoinColumn(name = "moto_id")
    private Moto moto;
}
