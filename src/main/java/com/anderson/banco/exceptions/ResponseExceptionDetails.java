package com.anderson.banco.exceptions;

import java.util.Date;

public class ResponseExceptionDetails {
	private Date data;
	private int codErro;
	private String nomeErro;
	private String mensagem;
	
	public ResponseExceptionDetails() {}
	
	public ResponseExceptionDetails(Date data, int codE, String nomeE, String msg) {
		this.data = data;
		this.codErro = codE;
		this.nomeErro = nomeE;
		this.mensagem = msg;
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getCodErro() {
		return codErro;
	}

	public void setCodErro(int codErro) {
		this.codErro = codErro;
	}

	public String getNomeErro() {
		return nomeErro;
	}

	public void setNomeErro(String nomeErro) {
		this.nomeErro = nomeErro;
	}
	
	
}
