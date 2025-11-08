package com.lorarch.challenge.dto;

import com.lorarch.challenge.model.TipoMovimento;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OcorrenciaDTO {

    @Null
    private Long id;

    @NotNull(message = "O tipo é obrigatório.")
    private TipoMovimento tipo;

    @Size(max = 200, message = "A descrição pode ter no máximo 200 caracteres.")
    private String descricao;

    @NotNull(message = "A data é obrigatória.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    @NotNull(message = "O custo é obrigatório.")
    @DecimalMin(value = "0.00", message = "O custo não pode ser negativo.")
    private BigDecimal custo;

    @NotNull(message = "Selecione a moto.")
    private Long motoId;

    @NotNull(message = "Selecione o setor.")
    private Long setorId;

    // Getters/Setters
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

    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }

    public Long getSetorId() { return setorId; }
    public void setSetorId(Long setorId) { this.setorId = setorId; }
}
