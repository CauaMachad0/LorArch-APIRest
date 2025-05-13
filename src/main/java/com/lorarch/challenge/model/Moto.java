package com.lorarch.challenge.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    private String placa;

    @Enumerated(EnumType.STRING)
    private StatusMoto status;

    private String cor;

    private LocalDate dataEntrada;

    @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL)
    private List<Ocorrencia> ocorrencias = new ArrayList<>();
}

