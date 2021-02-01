package com.anderson.banco.model.response;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.OneToMany;

//Anotação Entity é para informar que essa classe é uma entidade no BD

public class ContaModelResponse {

	private String id;
	private BigDecimal valor;
	private String nome;
	private String rg;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public boolean equals(Object objetoComparado){
		ContaModelResponse contaComparado = (ContaModelResponse) objetoComparado;
		if(this.getId().equals(contaComparado.getId()) && this.getRg().equals(contaComparado.getRg())) return true;
		else return false;
	}
}
