package com.anderson.banco.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.OneToMany;

//Anotação Entity é para informar que essa classe é uma entidade no BD

@Entity
public class Conta{
	
	//Anotação ID é para informar que esse atributo é a PK do BD
	
	@Id
	private String id;

	@Column()
	private BigDecimal valor;
	
	//Anotação Column é para configurar as propriedades da coluna
	//a propriedade nullable é para informar se é ou não null (NOT NULL)
	//a propriedade unique é para deixar a coluna como valor unico, ou seja, não repete nos registros
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, unique = true)
	private String rg;

	//relacionamento um para muito com a entidade Compra
	@OneToMany
	private List<Compra> compras;
	
	
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

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
}
