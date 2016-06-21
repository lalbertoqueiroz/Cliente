package br.com.cliente.dao;

import java.util.Date;
import java.util.List;

import br.com.cliente.modelo.Cliente;
import br.com.cliente.modelo.Fatura;
import br.com.cliente.modelo.Pagamento;
import br.com.cliente.modelo.Parcela;
import br.com.cliente.modelo.ParcelaAndFatura;

public interface IPagamentoDAO {

	
	
	/**
	 * Salvando Parcelas e Fatura
	 * @param Pagamento pagamento
	 * @throws Exception
	 */
	public void saveParcelasAndFatura(List<Pagamento> listPagamento) throws Exception;
	
	
	/**
	 * Obtendo Fatura
	 * @param Pagamento pagamento
	 * @throws Exception
	 */
	public Fatura procurarFaturaByCliente(Cliente  cliente) throws Exception;
	
	
	
	/**
	 * Obtendo List<ParcelaAndFatura>
	 * @param Cliente  cliente
	 * @throws Exception
	 */
	public List<ParcelaAndFatura> procurarParcelasAndFaturaByCliente(Cliente cliente , Date dataInicial , Date dataFinal) throws Exception;
	
	
	/**
	 * Integer id
	 * @param Parcela Parcela
	 * @throws Exception
	 */
	public Parcela procurarParcelasById(Integer id) throws Exception;
	
	
	
	
}
