package br.com.cliente.dao;

import java.util.List;

import br.com.cliente.modelo.Cliente;

public interface IClienteDAO {
	
	
	/**
	 * Salvando Cliente
	 * @param Cliente
	 * @throws Exception
	 */
	public void save(Cliente clienteVO) throws Exception;
	
	/**
	 * Alterando Cliente
	 * @param Cliente
	 * @throws Exception
	 */
	public void update(Cliente clienteVO) throws Exception;
	
	/**
	 * Alterando Cliente
	 * @param Cliente
	 * @throws Exception
	 */
	public void delete(Cliente clienteVO) throws Exception;
	
	/**
	 * Recupera por Nome
	 * @param Cliente
	 * @throws Exception
	 */
	public List<Cliente> findClienteByParameter(Cliente clienteVO)throws Exception;
	
	/**
	 * Recupera por id
	 * @param Cliente
	 * @throws Exception
	 */
	public Cliente findClienteById(Integer id) throws Exception;
	
}
