package br.com.cliente.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cliente.modelo.Plano;

@Service("planoDao")
public class PlanoDAOImpl implements IPlanoDAO {

	private static final Logger logger = Logger.getLogger(PlanoDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void save(Plano plano) throws Exception {
		logger.debug("Entry method save(Plano plano)");
		try {
			if (plano != null && plano.getId() != null && plano.getId() > 0) {
				entityManager.merge(plano);
			} else {
				entityManager.persist(plano);
			}
			entityManager.flush();

		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit save(Plano plano)");

	}

	@Transactional
	@Override
	public void update(Plano plano) throws Exception {
		logger.debug("Entry method update(Plano plano ");
		try {
			entityManager.merge(plano);
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw new Exception(e);
		}
		logger.debug("Exit method uupdate(Plano plano)");

	}

	@Transactional
	@Override
	public void delete(Plano plano) throws Exception {
		logger.debug("Entry method delete(Plano plano)");
		try {
			if (plano != null && plano.getId() != null) {
				plano = (Plano) findPlanoById(plano.getId());
				entityManager.remove(plano);
			}
			entityManager.flush();

		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit delete(Plano plano)");

	}

	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public List<Plano> findPlanoByParameter(Plano plano) throws Exception {
		logger.debug("Entry method findCustodianteByParameter(Plano plano)");
		List<Plano> list = null;
		Query query = null;
		try {
			StringBuilder select = new StringBuilder("select c from Plano as c where 1=1 ");
			String nome = plano.getNome();
			Integer id = plano.getId();
			if (nome != null && !nome.trim().equals("")) {
				select.append(" and  c.nome like '%" + nome + "%' ");
			}
			if (id != null && id > 0) {
				select.append(" and  c.id= " + id);
			}
			query = entityManager.createQuery(select.toString());

			list = (List<Plano>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("Entity not Fount nome Plano");
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findCustodianteByParameter(Plano plano)");
		return list;
	}

	@Transactional
	@Override
	public Plano findPlanoById(Integer id) throws Exception {
		logger.debug("Entry method findPlanoById(Integer id)");
		Plano plano = null;
		Query query = null;
		try {
			query = entityManager.createQuery("select c from Plano as c where c.id= " + id);
			plano = (Plano) query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount id " + id);
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findPlanoById(Integer id)");
		return plano;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Plano> findAll() throws Exception {
		logger.debug("Entry method findAll()");
		List<Plano> list = null;
		Query query = null;
		try {
			StringBuilder select = new StringBuilder("select c from Plano as c where 1=1 ");
			query = entityManager.createQuery(select.toString());
			list = (List<Plano>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("Entity not Fount email or Code Plano");
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findAll()");
		return list;
	}

}
