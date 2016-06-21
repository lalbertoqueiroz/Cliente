package br.com.cliente.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cliente.dao.IPagamentoDAO;
import br.com.cliente.modelo.Cliente;
import br.com.cliente.modelo.Fatura;
import br.com.cliente.modelo.Pagamento;
import br.com.cliente.modelo.Parcela;
import br.com.cliente.modelo.ParcelaAndFatura;

@Service("pagamentoService")
public class PagamentoService {
	
	
	@Autowired
	public IPagamentoDAO pagamentoDAO;
	
	
	/**
	 * Salvando Pagamento
	 * @param Pagamento pagamento
	 * @throws Exception
	 */
	public void saveParcelasAndFatura(List<Pagamento> listPagamento) throws Exception{
		pagamentoDAO.saveParcelasAndFatura(listPagamento);
	}
	
	
	
	/**
	 * get Fatura
	 * @param Cliente cliente
	 * @throws Exception
	 */
	public Fatura procurarFaturaByCliente(Cliente cliente) throws Exception {
		return pagamentoDAO.procurarFaturaByCliente(cliente);
	}
	
	
	
	/**
	 * procurarParcelasAndFaturaByCliente
	 * @param Cliente cliente , Date dataInicial , Date dataFinal
	 * @throws Exception
	 */
	public List<ParcelaAndFatura> procurarParcelasAndFaturaByCliente(Cliente cliente , Date dataInicial , Date dataFinal) throws Exception{
		return pagamentoDAO.procurarParcelasAndFaturaByCliente(cliente , dataInicial , dataFinal);
	}
	
	
	/**
	 * procurarParcelasById
	 * @param Integer id
	 * @throws Exception
	 */
	public Parcela procurarParcelasById(Integer id) throws Exception{
		return pagamentoDAO.procurarParcelasById(id);
	}
	

}
