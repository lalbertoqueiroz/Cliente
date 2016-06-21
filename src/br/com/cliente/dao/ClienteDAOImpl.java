package br.com.cliente.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cliente.modelo.Cliente;

@Service("clienteDao")
public class ClienteDAOImpl implements IClienteDAO {

	private static final Logger logger = Logger.getLogger(ClienteDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void save(Cliente clienteVO) throws Exception {
		logger.debug("Entry method save(Cliente clienteVO)");
		try {
			
			entityManager.persist(clienteVO);
			
			entityManager.flush();

		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit save(Cliente clienteVO)");

	}

	@Transactional
	@Override
	public void update(Cliente clienteVO) throws Exception {
		logger.debug("Entry method update(Cliente clienteVO ");
		try {
			entityManager.merge(clienteVO);
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw new Exception(e);
		}
		logger.debug("Exit method uupdate(Cliente clienteVO)");

	}

	@Transactional
	@Override
	public void delete(Cliente clienteVO) throws Exception {
		logger.debug("Entry method delete(Cliente clienteVO)");
		try {
			if (clienteVO != null && clienteVO.getId() != null) {
				clienteVO = (Cliente) findClienteById(clienteVO.getId());
				entityManager.remove(clienteVO);
			}
			entityManager.flush();

		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit delete(Cliente clienteVO)");

	}

	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public List<Cliente> findClienteByParameter(Cliente clienteVO) throws Exception {
		logger.debug("Entry method findClienteByParameter(ClienteVO clienteVO)");
		List<Cliente> list = null;
		Query query = null;
		try {
			StringBuilder select = new StringBuilder("select c from Cliente as c where 1=1 ");
			String nome = clienteVO.getNome();
			String cpf = clienteVO.getCpf();
			Boolean ehCliente = clienteVO.getEhCliente();
			if (nome != null && !nome.trim().equals("")) {
				select.append(" and  c.nome like '%" + nome + "%' ");
			}
			if (cpf != null && !cpf.trim().equals("")) {
				select.append(" and  c.cpf = '" + cpf + "' ");
			}
			if (ehCliente != null) {
				select.append(" and  c.ehCliente = " + ehCliente + " ");
			}
			query = entityManager.createQuery(select.toString());

			list = (List<Cliente>) query.getResultList();
		} catch (NoResultException e) {
			logger.debug("Entity not Fount nome Cliente");
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findClienteByParameter(Cliente clienteVO)");
		return list;
	}

	@Transactional
	@Override
	public Cliente findClienteById(Integer id) throws Exception {
		logger.debug("Entry method findPlanoById(Integer id)");
		Cliente clienteVO = null;
		Query query = null;
		try {
			query = entityManager.createQuery("select c from Cliente as c where c.id= " + id);
			clienteVO = (Cliente) query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount id " + id);
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findClienteById(Integer id)");
		return clienteVO;
	}

}
