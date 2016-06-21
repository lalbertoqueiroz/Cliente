package br.com.cliente.dao;

import java.util.List;

import br.com.cliente.modelo.Plano;

public interface IPlanoDAO {

	
	/**
	 * Salvando Plano
	 * @param plano
	 * @throws Exception
	 */
	public void save(Plano plano) throws Exception;
	
	/**
	 * Alterando Plano
	 * @param plano
	 * @throws Exception
	 */
	public void update(Plano plano) throws Exception;
	
	/**
	 * Alterando Plano
	 * @param plano
	 * @throws Exception
	 */
	public void delete(Plano plano) throws Exception;
	
	/**
	 * Recupera por Nome
	 * @param plano
	 * @throws Exception
	 */
	public List<Plano> findPlanoByParameter(Plano plano)throws Exception;
	
	/**
	 * Recupera por id
	 * @param plano
	 * @throws Exception
	 */
	public Plano findPlanoById(Integer id) throws Exception;
	
	/**
	 * Recupera todos os planos
	 * @param plano
	 * @throws Exception
	 */
	public List<Plano> findAll() throws Exception;
	
}
