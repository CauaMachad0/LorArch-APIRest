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
    @Column(name = "TIPO", nullable = false, length = 20)
    private TipoMovimento tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_MOV", nullable = false, length = 20)
    private TipoMovimento tipoMovimentoExtra;

    @Size(max = 200)
    @Column(name = "OBSERVACAO", length = 200)
    private String descricao;

    @NotNull
    @Column(name = "DATA_OC", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "DT_MOV", nullable = false)
    private LocalDate dataMovimento;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "CUSTO", nullable = false, precision = 12, scale = 2)
    private BigDecimal custo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MOTO_ID", nullable = false)
    private Moto moto;

    @Column(name = "ID_MOTO", nullable = false, updatable = false, insertable = true)
    private Long idMotoDuplicado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SETOR_ID", nullable = false)
    private Setor setor;

    @Column(name = "ID_SETOR", nullable = false, updatable = false, insertable = true)
    private Long idSetorDuplicado;

    @PrePersist
    public void prePersist() {
        if (data == null) data = LocalDate.now();
        if (dataMovimento == null) dataMovimento = this.data;
        if (custo == null) custo = BigDecimal.ZERO;
        if (this.moto != null) this.idMotoDuplicado = this.moto.getId();
        if (this.setor != null) this.idSetorDuplicado = this.setor.getId();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoMovimento getTipo() { return tipo; }
    public void setTipo(TipoMovimento tipo) { this.tipo = tipo; }

    public TipoMovimento getTipoMovimentoExtra() { return tipoMovimentoExtra; }

    public void setTipoMovimentoExtra(TipoMovimento tipoMovimentoExtra) {
        this.tipoMovimentoExtra = tipoMovimentoExtra;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalDate getDataMovimento() { return dataMovimento; }
    public void setDataMovimento(LocalDate dataMovimento) { this.dataMovimento = dataMovimento; }

    public BigDecimal getCusto() { return custo; }
    public void setCusto(BigDecimal custo) { this.custo = custo; }

    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }

    public Long getIdMotoDuplicado() {
        return idMotoDuplicado;
    }

    public void setIdMotoDuplicado(Long idMotoDuplicado) {
        this.idMotoDuplicado = idMotoDuplicado;
    }

    public Long getIdSetorDuplicado() {
        return idSetorDuplicado;
    }

    public void setIdSetorDuplicado(Long idSetorDuplicado) {
        this.idSetorDuplicado = idSetorDuplicado;
    }

    public Setor getSetor() { return setor; }
    public void setSetor(Setor setor) { this.setor = setor; }
}