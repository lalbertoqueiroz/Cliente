package br.com.cliente.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cliente.modelo.Conta;

@Service("contaDao")
public class ContaDAOImpl implements IContaDAO {

	private static final Logger logger = Logger.getLogger(ContaDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void save(Conta contaVO) throws Exception {
		logger.debug("Entry method save(Conta contaVO)");
		try {

			entityManager.persist(contaVO);

			entityManager.flush();

		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit save(Conta contaVO)");

	}

	@Transactional
	@Override
	public void update(Conta contaVO) throws Exception {
		logger.debug("Entry method update(Conta contaVO ");
		try {
			entityManager.merge(contaVO);
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw new Exception(e);
		}
		logger.debug("Exit method uupdate(Conta contaVO)");

	}

	@Transactional
	@Override
	public void delete(Conta contaVO) throws Exception {
		logger.debug("Entry method delete(Conta contaVO)");
		try {
			if (contaVO != null && contaVO.getId() != null) {
				contaVO = (Conta) findContaById(contaVO.getId());
				entityManager.remove(contaVO);
			}
			entityManager.flush();

		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit delete(Conta contaVO)");

	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Conta> findContaByParameter(Conta contaVO) throws Exception {
		logger.debug("Entry method findContaByParameter(Conta contaVO)");
		List<Conta> list = null;
		Query query = null;
		try {
			StringBuilder select = new StringBuilder("select c from Conta as c where 1=1 ");
			String nome = null;
			if (contaVO.getIdBeneficiario() != null) {
				nome = contaVO.getIdBeneficiario().getNome();
			}
			if (nome != null && !nome.equals("")) {
				select.append(" and  c.cliente.nome like '%" + nome + "%' ");
			}
			query = entityManager.createQuery(select.toString());

			list = (List<Conta>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("Entity not Fount nome Conta");
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findContaByParameter(Conta contaVO)");
		return list;
	}

	@Transactional
	@Override
	public Conta findContaById(Integer id) throws Exception {
		logger.debug("Entry method findContaById(Integer id)");
		Conta contaVO = null;
		Query query = null;
		try {
			query = entityManager.createQuery("select c from Conta as c where c.id= " + id);
			contaVO = (Conta) query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount id " + id);
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findContaById(Integer id)");
		return contaVO;
	}

}
