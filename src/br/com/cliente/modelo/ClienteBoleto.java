package br.com.cliente.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class ClienteBoleto {
	
	private Integer idCliente;
	
	private String nome;
	
	private String plano;
	
	private BigDecimal valorParcela;
	
	private BigDecimal valorFatura;
	
	private Date dataGeracao;
	
	private String fatura;
	
	private int qtsParcelas;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPlano() {
		return plano;
	}
	public void setPlano(String plano) {
		this.plano = plano;
	}
	public BigDecimal getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}
	public BigDecimal getValorFatura() {
		return valorFatura;
	}
	public void setValorFatura(BigDecimal valorFatura) {
		this.valorFatura = valorFatura;
	}
	public Date getDataGeracao() {
		return dataGeracao;
	}
	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}
	public String getFatura() {
		return fatura;
	}
	public void setFatura(String fatura) {
		this.fatura = fatura;
	}
	public int getQtsParcelas() {
		return qtsParcelas;
	}
	public void setQtsParcelas(int qtsParcelas) {
		this.qtsParcelas = qtsParcelas;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	
	

}
