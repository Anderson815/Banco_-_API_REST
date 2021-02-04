package com.anderson.banco.model.request;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CompraModelRequest {

    @NotBlank(message = "O título não foi informado") //validação de nulo ou vazio (só para Strings)
    private String titulo;
    @NotNull(message = "O valor não foi informado") //validação de nulo (para objetos)
    private BigDecimal valor;

    public String getTitulo() {
        return titulo;
    }
    public BigDecimal getValor() {
        return valor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
