package br.com.cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cliente.dao.IPlanoDAO;
import br.com.cliente.modelo.Plano;

@Service("planoService")
public class PlanoService {
	
	@Autowired
	public IPlanoDAO planoDao;
	
	
	/**
	 * Salvando Plano
	 * @param plano
	 * @throws Exception
	 */
	public void save(Plano plano) throws Exception{
		planoDao.save(plano);
	}
	
	/**
	 * Alterando Plano
	 * @param plano
	 * @throws Exception
	 */
	public void update(Plano plano) throws Exception{
		planoDao.update(plano);
	}
	
	
	/**
	 * Alterando Plano
	 * @param plano
	 * @throws Exception
	 */
	public void delete(Plano plano) throws Exception{
		planoDao.delete(plano);
	}
	
	/**
	 * Recupera por Nome
	 * @param plano
	 * @throws Exception
	 */
	public List<Plano> findPlanoByParameter(Plano plano)throws Exception{
		return planoDao.findPlanoByParameter(plano);
	}
	
	/**
	 * Recupera por id
	 * @param plano
	 * @throws Exception
	 */
	public Plano findPlanoById(Integer id) throws Exception{
		return planoDao.findPlanoById(id);
	}
	
	/**
	 * Recupera todos os planos
	 * @param plano
	 * @throws Exception
	 */
	public List<Plano> findAll() throws Exception{
		return planoDao.findAll();
	}

}
