package com.lorarch.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "MOTO", schema = "RM558024")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(max = 10)
    @Column(name = "PLACA", nullable = false, unique = true, length = 10)
    private String placa;

    @NotBlank
    @Size(max = 50)
    @Column(name = "MODELO", nullable = false, length = 50)
    private String modelo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private StatusMoto status;

    @NotBlank
    @Size(max = 50)
    @Column(name = "SETOR", nullable = false, length = 50)
    private String setor;

    // TIMESTAMP no Oracle ↔ LocalDateTime no Java
    @CreationTimestamp
    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private LocalDateTime dataAtualizacao;

    // getters/setters

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
}
