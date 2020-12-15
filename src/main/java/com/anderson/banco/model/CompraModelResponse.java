package com.anderson.banco.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class CompraModelResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private BigDecimal valor;
    private Date data;

    //Relacionamento muito para um com a entidade Conta
    @ManyToOne
    private ContaModelResponse contaModelResponse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
