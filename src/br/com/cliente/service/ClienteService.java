package br.com.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cliente.dao.IClienteDAO;
import br.com.cliente.modelo.Cliente;

@Service("clienteService")
public class ClienteService {
	
	
	@Autowired
	public IClienteDAO clienteDao;
	
	
	/**
	 * Salvando Cliente
	 * @param cliente
	 * @throws Exception
	 */
	public void save(Cliente cliente) throws Exception{
		clienteDao.save(cliente);
	}
	
	/**
	 * Alterando Cliente
	 * @param Cliente
	 * @throws Exception
	 */
	public void update(Cliente cliente) throws Exception{
		clienteDao.update(cliente);
	}
	
	
	/**
	 * Alterando Cliente
	 * @param Cliente
	 * @throws Exception
	 */
	public void delete(Cliente cliente) throws Exception{
		clienteDao.delete(cliente);
	}
	
	/**
	 * Recupera por Nome
	 * @param plano
	 * @throws Exception
	 */
	public List<Cliente> findClienteByParameter(Cliente cliente)throws Exception{
		return clienteDao.findClienteByParameter(cliente);
	}
	
	/**
	 * Recupera por id
	 * @param cliente
	 * @throws Exception
	 */
	public Cliente findClienteById(Integer id) throws Exception{
		return clienteDao.findClienteById(id);
	}
}
