package br.com.cliente.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="FATURA")
public class Fatura implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	
	@Column(name="PATH_FATURA")
	private String pathFatura;
	
	
	@ManyToOne
	@JoinColumn(name = "ID_CLIENTE")
	private Cliente cliente;
	
	
	@Column(name="DATA_GERACAO")
	private Date dataGeracao;
	
	@Column(name="QTD_PARCELAS")
	private Integer qtdParcelas;
	
	
	@Column(name="VL_FATURA")
	private BigDecimal valorFatura;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getPathFatura() {
		return pathFatura;
	}


	public void setPathFatura(String pathFatura) {
		this.pathFatura = pathFatura;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Date getDataGeracao() {
		return dataGeracao;
	}


	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}


	public Integer getQtdParcelas() {
		return qtdParcelas;
	}


	public void setQtdParcelas(Integer qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}


	public BigDecimal getValorFatura() {
		return valorFatura;
	}


	public void setValorFatura(BigDecimal valorFatura) {
		this.valorFatura = valorFatura;
	}
	
	
	
}
