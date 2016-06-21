package br.com.cliente.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CONTA")
public class Conta {
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="DESC_BANCO")
	private String cdBanco;
	
	
	@OneToOne
	@JoinColumn(name = "ID_BENEFICIARIO")
	private Cliente idBeneficiario;
	
	@Column(name="DESCRICAO_CONTA")
	private String descConta;
	
	@Column(name="COD_CARTEIRA")
	private Integer codCarteira;
	
	@Column(name="COD_AGENCIA")
	private String codAgencia;
	
	@Column(name="COD_BENEFICIARIO")
	private String codBeneficiario;
	
	@Column(name="COD_CONTA")
	private String codConta;
	
	
	@Column(name="COD_SEQUENCIAL")
	private BigDecimal codSequencial;
	
	@Column(name="IOF")
	private BigDecimal iof;
	
	@Column(name="MULTA")
	private BigDecimal multa;
	
	@Column(name="JUROS")
	private BigDecimal juros;
	
	@Column(name="DIAS_VENCIMENTO")
	private BigDecimal diasVencimento;
	
	@Column(name="SEM_VALOR")
	private Boolean semValor;
	
	@Column(name="DESC_INSTRUCAO")
	private String descInstrucao;
	
	@Column(name="TIP_TITULO")
	private String tipTitulo;
	
	@Column(name="ACEITE")
	private Boolean aceite;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCdBanco() {
		return cdBanco;
	}

	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}

	public Cliente getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(Cliente idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public String getDescConta() {
		return descConta;
	}

	public void setDescConta(String descConta) {
		this.descConta = descConta;
	}

	public Integer getCodCarteira() {
		return codCarteira;
	}

	public void setCodCarteira(Integer codCarteira) {
		this.codCarteira = codCarteira;
	}

	public String getCodAgencia() {
		return codAgencia;
	}

	public void setCodAgencia(String codAgencia) {
		this.codAgencia = codAgencia;
	}

	public String getCodBeneficiario() {
		return codBeneficiario;
	}

	public void setCodBeneficiario(String codBeneficiario) {
		this.codBeneficiario = codBeneficiario;
	}

	public String getCodConta() {
		return codConta;
	}

	public void setCodConta(String codConta) {
		this.codConta = codConta;
	}

	public BigDecimal getCodSequencial() {
		return codSequencial;
	}

	public void setCodSequencial(BigDecimal codSequencial) {
		this.codSequencial = codSequencial;
	}

	public BigDecimal getIof() {
		return iof;
	}

	public void setIof(BigDecimal iof) {
		this.iof = iof;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public BigDecimal getDiasVencimento() {
		return diasVencimento;
	}

	public void setDiasVencimento(BigDecimal diasVencimento) {
		this.diasVencimento = diasVencimento;
	}

	public Boolean getSemValor() {
		return semValor;
	}

	public void setSemValor(Boolean semValor) {
		this.semValor = semValor;
	}

	public String getDescInstrucao() {
		return descInstrucao;
	}

	public void setDescInstrucao(String descInstrucao) {
		this.descInstrucao = descInstrucao;
	}

	public String getTipTitulo() {
		return tipTitulo;
	}

	public void setTipTitulo(String tipTitulo) {
		this.tipTitulo = tipTitulo;
	}

	public Boolean getAceite() {
		return aceite;
	}

	public void setAceite(Boolean aceite) {
		this.aceite = aceite;
	}
	
}
