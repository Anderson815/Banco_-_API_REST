package com.anderson.banco.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class CompraModelDb {

    @Id //Identifica como o PK da entidade Compra
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera a chave primaria de forma automática
    private int id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne //Relacionamento muito para um com a entidade Conta
    @JoinColumn(name = "conta_id") //Nome do campo do campo FK na entidade Compra
    private ContaModelDb conta;

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
    public ContaModelDb getConta() {
        return conta;
    }
    public void setConta(ContaModelDb contaModelDb) {
        this.conta = contaModelDb;
    }
}
