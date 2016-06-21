package br.com.cliente.modelo;

import java.io.Serializable;
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
@Table(name="CLIENTE")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="EMAIL_COMERCIAL")
	private String emailComercial;
	
	@Column(name="EMAIL_PESSOAL")
	private String emailPessoal;
	
	@Column(name="CPF_CNPJ")
	private String cpf;
	
	@Column(name="CEP")
	private String cep;
	
	@Column(name="RUA")
	private String rua;
	
	@Column(name="NUMERO")
	private String numero;
	
	@Column(name="BAIRRO")
	private String bairro;
	
	@Column(name="COMPLEMENTO")
	private String complemento;
	
	@Column(name="FONE_FIXO")
	private String  fonefixo;
	
	@Column(name="CEL_COMERCIAL")
	private String  celularComercial;
	
	@Column(name="CEL_PESSOAL")
	private String  celularPessoal;
	
	@OneToOne
	@JoinColumn(name = "ID_PLANO")
	private Plano plano;
	
	
	@Column(name="VALOR_PLANO")
	private BigDecimal  valor;
	
	@Column(name="EH_CLIENTE")
	public Boolean ehCliente;
	
	
	@Column(name="EH_CPF_CNPJ")
	public Boolean ehCpfCnpj;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmailComercial() {
		return emailComercial;
	}

	public void setEmailComercial(String emailComercial) {
		this.emailComercial = emailComercial;
	}

	public String getEmailPessoal() {
		return emailPessoal;
	}

	public void setEmailPessoal(String emailPessoal) {
		this.emailPessoal = emailPessoal;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getFonefixo() {
		return fonefixo;
	}

	public void setFonefixo(String fonefixo) {
		this.fonefixo = fonefixo;
	}

	public String getCelularComercial() {
		return celularComercial;
	}

	public void setCelularComercial(String celularComercial) {
		this.celularComercial = celularComercial;
	}

	public String getCelularPessoal() {
		return celularPessoal;
	}

	public void setCelularPessoal(String celularPessoal) {
		this.celularPessoal = celularPessoal;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Boolean getEhCliente() {
		return ehCliente;
	}

	public void setEhCliente(Boolean ehCliente) {
		this.ehCliente = ehCliente;
	}

	public Boolean getEhCpfCnpj() {
		return ehCpfCnpj;
	}

	public void setEhCpfCnpj(Boolean ehCpfCnpj) {
		this.ehCpfCnpj = ehCpfCnpj;
	}

	
}
