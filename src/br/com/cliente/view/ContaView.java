package br.com.cliente.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.cliente.modelo.Cliente;
import br.com.cliente.modelo.Conta;
import br.com.cliente.service.ClienteService;
import br.com.cliente.service.ContaService;

@WebServlet("/contaView")
public class ContaView extends AbstractHttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ClienteService clienteService;
	
	@Autowired
	public ContaService contaService ;
	
	
	public void salvarConta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/json");
		JsonObject myObj = new JsonObject();
		try {
			String cdBanco = getParameter("codBanco", request);
			String idBeneficiario = getParameter("beneficiarioId", request);
			String descricao = getParameter("descricao", request);
			String codCarteira = getParameter("codCarteira", request);
			String codAgencia = getParameter("codAgencia", request);
			String codBeneficiario = getParameter("codBeneficiario", request);
			String codConta = getParameter("codConta", request);
			String codNossoSequencial = getParameter("codNossoSequencial", request);
			String iof = getParameter("iofSeguradora", request);
			String multa = getParameter("multa", request);
			String juros = getParameter("juros", request);
			String diasVencimento = getParameter("diasVencimento", request);
			String semValor = getParameter("semValor", request);
			String descInstrucao = getParameter("descInstrucao", request);
			String tipoTitulo = getParameter("tipoTitulo", request);
			String aceite = getParameter("aceite", request);

			Conta conta = new Conta();
			conta.setCdBanco(cdBanco);
			if(idBeneficiario != null && !idBeneficiario.trim().equals("")){
				Cliente cliente = clienteService.findClienteById(Integer.valueOf(idBeneficiario));
				conta.setIdBeneficiario(cliente);
			}
			conta.setDescConta(descricao);
			conta.setCodCarteira(Integer.valueOf(codCarteira));
			conta.setCodAgencia(codAgencia);
			conta.setCodBeneficiario(codBeneficiario);
			conta.setCodConta(codConta);
			conta.setCodSequencial(new BigDecimal(codNossoSequencial));
			conta.setIof(new BigDecimal(iof));
			conta.setMulta(new BigDecimal(multa));
			conta.setJuros(new BigDecimal(juros));
			conta.setDiasVencimento(new BigDecimal(diasVencimento));
			conta.setSemValor(Boolean.valueOf(isSemValor(semValor)));
			conta.setDescInstrucao(descInstrucao);
			conta.setTipTitulo(tipoTitulo);
			conta.setAceite(Boolean.valueOf(isAceite(aceite)));
			
			
			

			contaService.save(conta);

			
			myObj.addProperty("success", Boolean.TRUE);

		} catch (Exception e) {
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na inserção do Conta. Verificar logs servidor");
			e.printStackTrace();

		} finally {
			System.out.println("json = " + myObj.toString());
			out.println(myObj.toString());
			out.close();

		}

	}
	
	
	public void alterar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/json");
		JsonObject myObj = new JsonObject();
		try {
			
			String idStr = getParameter("idConta", request);
			String cdBanco = getParameter("codBanco", request);
			String idBeneficiario = getParameter("beneficiarioId", request);
			String descricao = getParameter("descricao", request);
			String codCarteira = getParameter("codCarteira", request);
			String codAgencia = getParameter("codAgencia", request);
			String codBeneficiario = getParameter("codBeneficiario", request);
			String codConta = getParameter("codConta", request);
			String codNossoSequencial = getParameter("codNossoSequencial", request);
			String iof = getParameter("iofSeguradora", request);
			String multa = getParameter("multa", request);
			String juros = getParameter("juros", request);
			String diasVencimento = getParameter("diasVencimento", request);
			String semValor = getParameter("semValor", request);
			String descInstrucao = getParameter("descInstrucao", request);
			String tipoTitulo = getParameter("tipoTitulo", request);
			String aceite = getParameter("aceite", request);

			Conta conta = new Conta();
			conta.setCdBanco(cdBanco);
			if(idBeneficiario != null && !idBeneficiario.trim().equals("")){
				Cliente cliente = clienteService.findClienteById(Integer.valueOf(idBeneficiario));
				conta.setIdBeneficiario(cliente);
			}
			Integer id = null;
			if (idStr != null && !"".equals(idStr.trim())) {
				id = Integer.valueOf(idStr);
				conta.setId(id);
			}
			conta.setDescConta(descricao);
			conta.setCodCarteira(Integer.valueOf(codCarteira));
			conta.setCodAgencia(codAgencia);
			conta.setCodBeneficiario(codBeneficiario);
			conta.setCodConta(codConta);
			conta.setCodSequencial(new BigDecimal(codNossoSequencial));
			conta.setIof(new BigDecimal(iof));
			conta.setMulta(new BigDecimal(multa));
			conta.setJuros(new BigDecimal(juros));
			conta.setDiasVencimento(new BigDecimal(diasVencimento));
			conta.setSemValor(Boolean.valueOf(isSemValor(semValor)));
			conta.setDescInstrucao(descInstrucao);
			conta.setTipTitulo(tipoTitulo);
			conta.setAceite(Boolean.valueOf(isAceite(aceite)));
			
			
			

			contaService.update(conta);

			
			myObj.addProperty("success", Boolean.TRUE);

		} catch (Exception e) {
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na inserção do Conta. Verificar logs servidor");
			e.printStackTrace();

		} finally {
			System.out.println("json = " + myObj.toString());
			out.println(myObj.toString());
			out.close();

		}

	}
	
	
	public void procurar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomeCliente = getParameter("nomeBeneficiario", request);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Conta conta = new Conta();
			Cliente cliente = new Cliente();
			cliente.setNome(nomeCliente);
			conta.setIdBeneficiario(cliente);

			List<Conta> list = contaService.findContaByParameter(conta);

			response.setContentType("text/json");
			Gson gson = new Gson();
			String listContas = gson.toJson(list);
			System.out.println("Contas = " + listContas);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listContas);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Cliente. Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}
	
	
	public void procurarConta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = getParameter("id", request);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			Integer id = null;
			if (idStr != null && !"".equals(idStr.trim())) {
				id = Integer.valueOf(idStr);
			}
			Conta conta = contaService.findContaById(id);
			
			response.setContentType("text/json");
			Gson gson = new Gson();
			String contaJson = gson.toJson(conta);
			System.out.println("Conta = " + contaJson);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("conta", contaJson);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Conta.Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}
	
	public void deletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			String idStr = getParameter("id", request);

			Conta conta = new Conta();
			Integer id = null;
			if (idStr != null && !"".equals(idStr.trim())) {
				id = Integer.valueOf(idStr);
			}
			conta.setId(id);

			contaService.delete(conta);
			response.setContentType("text/json");

			myObj.addProperty("success", Boolean.TRUE);
			System.out.println("json = " + myObj.toString());

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Remoção do Cliente. Verificar logs servidor");

		} finally {
			out.println(myObj.toString());
			out.close();

		}

	}
	
	public void obterContas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			List<Conta> list = contaService.findContaByParameter(new Conta());

			response.setContentType("text/json");
			Gson gson = new Gson();
			String listContas = gson.toJson(list);
			System.out.println("Contas = " + listContas);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listContas);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na obtenção das Contas. Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}

	
	private Boolean isSemValor(String semValor){
		return semValor != null && semValor.equalsIgnoreCase("semValor");
	}
	
	private Boolean isAceite(String isAceite){
		return isAceite != null && isAceite.equalsIgnoreCase("aceite");
	}
	
	
	@Override
	public Class<ContaView> getMethodClass() {
		return ContaView.class;
	}

}
