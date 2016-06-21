package br.com.cliente.modelo;

import java.util.Date;

public class DadosComplementar {
	
	private Date dateGeracao;
	
	private Date dateVencimento;

	public Date getDateVencimento() {
		return dateVencimento;
	}

	public void setDateVencimento(Date dateVencimento) {
		this.dateVencimento = dateVencimento;
	}

	public Date getDateGeracao() {
		return dateGeracao;
	}

	public void setDateGeracao(Date dateGeracao) {
		this.dateGeracao = dateGeracao;
	}

}
