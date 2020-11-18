package com.anderson.banco.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Column;
import com.sun.istack.NotNull;

//Anotação Entity é para informar que essa classe é uma entidade no BD

@Entity
public class Conta{
	
	//Anotação ID é para informar que esse atributo é a PK do BD
	
	@Id
	private String id;

	@Column()
	private double valor;
	
	//Anotação Column é para configurar as propriedades da coluna
	//a propriedade nullable é para informar se é ou não null (NOT NULL)
	//a propriedade unique é para deixar a coluna como valor unico, ou seja, não repete nos registros
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, unique = true)
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
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
