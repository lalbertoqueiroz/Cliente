package br.com.cliente.modelo;

import java.util.List;

public class Pagamento {
	
	
	private Fatura fatura;
	
	private List<Parcela> listPacerla;

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public List<Parcela> getListPacerla() {
		return listPacerla;
	}

	public void setListPacerla(List<Parcela> listPacerla) {
		this.listPacerla = listPacerla;
	}

}
