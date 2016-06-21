package br.com.cliente.dao;

import java.util.List;

import br.com.cliente.modelo.Conta;

public interface IContaDAO {
	
	
	/**
	 * Salvando Conta
	 * @param Conta
	 * @throws Exception
	 */
	public void save(Conta contaVO) throws Exception;
	
	/**
	 * Alterando Conta
	 * @param Conta
	 * @throws Exception
	 */
	public void update(Conta contaVO) throws Exception;
	
	/**
	 * Alterando Conta
	 * @param Conta
	 * @throws Exception
	 */
	public void delete(Conta contaVO) throws Exception;
	
	/**
	 * Recupera por Nome
	 * @param Conta
	 * @throws Exception
	 */
	public List<Conta> findContaByParameter(Conta contaVO)throws Exception;
	
	/**
	 * Recupera por id
	 * @param Conta
	 * @throws Exception
	 */
	public Conta findContaById(Integer id) throws Exception;
	

}
