package com.anderson.banco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class CompraModelResponse {

    @Id //Identifica como o PK da entidade Compra
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera a chave primaria de forma automática
    private int id;
    private String titulo;
    private BigDecimal valor;


    @ManyToOne //Relacionamento muito para um com a entidade Conta
    @JoinColumn(name = "conta_id") //Nome do campo do campo FK na entidade Compra
    @JsonIgnore //Não serializar essa propriedade. OBS: get e set tem que ter o mesmo nome do campo
    private ContaModelResponse conta;

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

    public ContaModelResponse getConta() {
        return conta;
    }

    public void setConta(ContaModelResponse contaModelResponse) {
        this.conta = contaModelResponse;
    }
}
