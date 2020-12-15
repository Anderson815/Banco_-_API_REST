package com.anderson.banco.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class CompraModelRequest {

    @NotBlank(message = "O nome é obrigatório")
    @Min(value = 5, message = "É necessário um nome de pelo menos 5 caracteres")
    private String titulo;
    @NotNull(message = "O valor não foi informado")
    private BigDecimal valor;
    @NotNull(message = "A data não foi informada")
    private Date data;

    //Relacionamento muito para um com a entidade Conta
    @ManyToOne
    private ContaModelResponse contaModelResponse;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ContaModelResponse getConta() {
        return contaModelResponse;
    }

    public void setConta(ContaModelResponse contaModelResponse) {
        this.contaModelResponse = contaModelResponse;
    }
}
