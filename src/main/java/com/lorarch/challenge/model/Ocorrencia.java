package com.lorarch.challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "OCORRENCIA", schema = "RM558024")
@SequenceGenerator(
        name = "seq_ocorrencia",
        sequenceName = "SEQ_OCORRENCIA",
        schema = "RM558024",
        allocationSize = 1
)
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ocorrencia")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_MOV", nullable = false, length = 20)
    private TipoMovimento tipo;

    @Size(max = 200)
    @Column(name = "OBSERVACAO", length = 200)
    private String descricao;

    @NotNull
    @Column(name = "DT_MOV", nullable = false)
    private LocalDate data;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "CUSTO", nullable = false, precision = 12, scale = 2)
    private BigDecimal custo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_MOTO", nullable = false)   // colunas reais da tabela
    private Moto moto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_SETOR", nullable = false)
    private Setor setor;

    @PrePersist
    public void prePersist() {
        if (data == null) data = LocalDate.now();
        if (custo == null) custo = BigDecimal.ZERO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoMovimento getTipo() { return tipo; }
    public void setTipo(TipoMovimento tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public BigDecimal getCusto() { return custo; }
    public void setCusto(BigDecimal custo) { this.custo = custo; }

    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }

    public Setor getSetor() { return setor; }
    public void setSetor(Setor setor) { this.setor = setor; }
}
