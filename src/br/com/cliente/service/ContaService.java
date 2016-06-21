package br.com.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cliente.dao.IContaDAO;
import br.com.cliente.modelo.Conta;

@Service("contaService")
public class ContaService {
	
	
	@Autowired
	public IContaDAO contaDao;
	
	
	/**
	 * Salvando Conta
	 * @param conta
	 * @throws Exception
	 */
	public void save(Conta conta) throws Exception{
		contaDao.save(conta);
	}
	
	/**
	 * Alterando Conta
	 * @param conta
	 * @throws Exception
	 */
	public void update(Conta conta) throws Exception{
		contaDao.update(conta);
	}
	
	
	/**
	 * Alterando Conta
	 * @param Exception
	 * @throws Exception
	 */
	public void delete(Conta conta) throws Exception{
		contaDao.delete(conta);
	}
	
	/**
	 * Recupera por Nome
	 * @param Exception
	 * @throws Exception
	 */
	public List<Conta> findContaByParameter(Conta conta)throws Exception{
		return contaDao.findContaByParameter(conta);
	}
	
	/**
	 * Recupera por id
	 * @param conta
	 * @throws Exception
	 */
	public Conta findContaById(Integer id) throws Exception{
		return contaDao.findContaById(id);
	}

}
