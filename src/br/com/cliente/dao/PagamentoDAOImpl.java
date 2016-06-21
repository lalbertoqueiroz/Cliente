package br.com.cliente.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cliente.modelo.Cliente;
import br.com.cliente.modelo.Fatura;
import br.com.cliente.modelo.Pagamento;
import br.com.cliente.modelo.Parcela;
import br.com.cliente.modelo.ParcelaAndFatura;

@Service("pagamentoDao")
public class PagamentoDAOImpl implements IPagamentoDAO {

	private static final Logger logger = Logger.getLogger(PagamentoDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void saveParcelasAndFatura(List<Pagamento> listPagamento) throws Exception {
		logger.debug("Entry saveParcelasAndFatura(Pagamento pagamento");
		try {

			Date dataGeracao = new Date();
			deleteByDataGeracao(dataGeracao, listPagamento);
			for (Pagamento pagamento : listPagamento) {
				List<Parcela> list = pagamento.getListPacerla();
				for (Parcela parcela : list) {
					parcela.setDataGeracao(dataGeracao);
					entityManager.persist(parcela);
					entityManager.flush();
				}
				Fatura fatura = pagamento.getFatura();
				fatura.setDataGeracao(dataGeracao);
				entityManager.persist(fatura);
				entityManager.flush();
			}

		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit saveParcelasAndFatura(Pagamento pagamento");

	}

	@SuppressWarnings("unchecked")
	private void deleteByDataGeracao(Date dataGeracao, List<Pagamento> listPagamento) {
		Query query = null;
		for (Pagamento pagamento : listPagamento) {
			Fatura fatura = pagamento.getFatura();
			try {
				query = entityManager.createQuery("select c from Parcela as c where c.cliente.id = "
						+ fatura.getCliente().getId() + " and  c.dataGeracao  = :dateGeracao");
				query.setParameter("dateGeracao", dataGeracao, TemporalType.DATE);
				List<Parcela> listDB = (List<Parcela>) query.getResultList();
				for (Parcela parcelaBD : listDB) {
					entityManager.remove(parcelaBD);
				}
			} catch (NoResultException e) {
				logger.debug("Erro : " + e);
			}
			fatura.setDataGeracao(dataGeracao);
			try {
				query = entityManager.createQuery("select c from Fatura as c where c.cliente.id = "
						+ fatura.getCliente().getId() + " and  c.dataGeracao  = :dateGeracao");
				query.setParameter("dateGeracao", dataGeracao, TemporalType.DATE);
				Fatura faturaBD = (Fatura) query.getSingleResult();
				if (faturaBD != null) {
					entityManager.remove(faturaBD);
				}
			} catch (NoResultException e) {
				logger.debug("Erro : " + e);
			}
		}
	}

	@Transactional
	@Override
	public Fatura procurarFaturaByCliente(Cliente cliente) throws Exception {
		logger.debug("Entry procurarFaturaByCliente(Cliente  cliente)");
		Fatura fatura = null;
		Query query = null;
		try {
			Date dataGeracao = new Date();
			query = entityManager.createQuery("select c from Fatura as c where c.cliente.id= " + cliente.getId()
					+ " and  c.dataGeracao  = :dateGeracao");
			query.setParameter("dateGeracao", dataGeracao, TemporalType.DATE);
			fatura = (Fatura) query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount id " + cliente.getId());
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit procurarFaturaByCliente(Cliente  cliente)");
		return fatura;
	}

	public Fatura procurarFaturaByClienteAndDataGeracao(Cliente cliente, Date dataGeracao) throws Exception {
		logger.debug("Entry procurarFaturaByCliente(Cliente  cliente)");
		Fatura fatura = null;
		Query query = null;
		try {
			query = entityManager.createQuery("select c from Fatura as c where c.cliente.id= " + cliente.getId()
					+ " and  c.dataGeracao  = :dateGeracao");
			query.setParameter("dateGeracao", dataGeracao, TemporalType.DATE);
			fatura = (Fatura) query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount id " + cliente.getId());
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit procurarFaturaByCliente(Cliente  cliente)");
		return fatura;
	}

	@SuppressWarnings("unchecked")
	public List<Parcela> procurarParcelaByCliente(Cliente cliente, Date dataInicial, Date dataFinal) throws Exception {
		logger.debug("Entry procurarParcelaByCliente(Cliente  cliente)");
		List<Parcela> list = null;
		Query query = null;
		try {
			String complementoQuery = "";
			if (dataFinal != null) {
				complementoQuery = "and  p.dataGeracao <= :dataFinal";
			}
			query = entityManager.createQuery("select p from Parcela as p where p.cliente.id= " + cliente.getId()
					+ " and  p.dataGeracao  >= :dataInicial " + complementoQuery);
			query.setParameter("dataInicial", dataInicial, TemporalType.DATE);
			if (!"".equals(complementoQuery)) {
				query.setParameter("dataFinal", dataFinal, TemporalType.DATE);
			}
			list = (List<Parcela>) query.getResultList();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount id " + cliente.getId());
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit procurarParcelaByCliente(Cliente  cliente)");
		return list;
	}

	@Transactional
	@Override
	public List<ParcelaAndFatura> procurarParcelasAndFaturaByCliente(Cliente cliente, Date dataInicial, Date dataFinal)
			throws Exception {
		logger.debug("Entry procurarParcelasAndFaturaByCliente(Cliente  cliente)");
		List<ParcelaAndFatura> listParcelaAndFaturas = null;
		try {
			cliente = findClienteById(cliente.getId());
			if (cliente != null) {
				listParcelaAndFaturas = new ArrayList<ParcelaAndFatura>();
				List<Parcela> list = procurarParcelaByCliente(cliente, dataInicial, dataFinal);
				for (Parcela parcela : list) {
					ParcelaAndFatura parcelaAndFatura = new ParcelaAndFatura();
					parcelaAndFatura.setCliente(cliente);
					parcelaAndFatura.setParcela(parcela);
					Fatura fatura = procurarFaturaByClienteAndDataGeracao(cliente, parcela.getDataGeracao());
					parcelaAndFatura.setFatura(fatura);
					listParcelaAndFaturas.add(parcelaAndFatura);
				}
			}

		} catch (NoResultException e) {
			logger.debug("Entity not Fount id " + cliente.getId());
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit procurarParcelasAndFaturaByCliente(Cliente  cliente)");

		return listParcelaAndFaturas;
	}

	public Cliente findClienteById(Integer id) throws Exception {
		logger.debug("Entry method findClienteById(Integer id)");
		Cliente clienteVO = null;
		Query query = null;
		try {
			query = entityManager.createQuery("select c from Cliente as c where c.id= " + id);
			clienteVO = (Cliente) query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount nome Cliente");
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method findClienteById(Integer id)");
		return clienteVO;
	}
	
	public Parcela procurarParcelasById(Integer id) throws Exception {
		logger.debug("Entry method procurarParcelasById(Integer id) ");
		Parcela parcela = null;
		Query query = null;
		try {
			query = entityManager.createQuery("select c from Parcela as c where c.id= " + id);
			parcela = (Parcela) query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Entity not Fount nome Cliente");
		} catch (Exception e) {
			logger.debug("Error:", e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("Exit method procurarParcelasById(Integer id) ");
		return parcela;
	}

}
