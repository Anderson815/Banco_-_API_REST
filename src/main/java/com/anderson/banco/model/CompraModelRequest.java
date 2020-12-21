package com.anderson.banco.model;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class CompraModelRequest {

    @NotBlank(message = "O nome está vazio")
    private String titulo;
    @NotBlank(message = "O valor não foi informado")
    private BigDecimal valor;


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


}
