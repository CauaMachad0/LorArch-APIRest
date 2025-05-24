package com.lorarch.challenge.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Enumerated(EnumType.STRING)
    private StatusMoto status;

    @Column(nullable = false)
    private String setor;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL)
    private List<Ocorrencia> ocorrencias;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Moto() {
    }

    public Moto(Long id, String placa, String modelo, StatusMoto status, String setor) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.status = status;
        this.setor = setor;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public StatusMoto getStatus() { return status; }
    public void setStatus(StatusMoto status) { this.status = status; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public List<Ocorrencia> getOcorrencias() { return ocorrencias; }
    public void setOcorrencias(List<Ocorrencia> ocorrencias) { this.ocorrencias = ocorrencias; }
}
