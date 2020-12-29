package com.anderson.banco.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContaModelRequest {

    @NotBlank(message = "O nome não foi informado") //validação de nulo ou vazio (só para Strings)
    private String nome;
    @NotNull(message = "O RG não foi informado") //validação de nulo (para objetos)
    @Size(min = 9, max = 9, message = "O RG não é valido, verifique se o mesmo está correto") //validação de tamanho (só para Strings)
    private String rg;

    public String getNome() {
        return nome;
    }

    public String getRg() {
        return rg;
    }
}
