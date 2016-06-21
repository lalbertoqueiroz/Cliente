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
import br.com.cliente.modelo.Plano;
import br.com.cliente.service.ClienteService;
import br.com.cliente.service.PlanoService;

@WebServlet("/clienteView")
public class ClienteView extends AbstractHttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ClienteService clienteService;

	@Autowired
	public PlanoService planoService;

	private Boolean isCPF(String cnpjCpf) {
		return cnpjCpf != null && cnpjCpf.equalsIgnoreCase("cpf");
	}

	private Boolean isCliente(String clienteBene) {
		return clienteBene != null && clienteBene.equalsIgnoreCase("cliente");
	}

	public void salvarCliente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/json");
		JsonObject myObj = new JsonObject();
		try {
			String nome = getParameter("nome", request);
			String emailComercial = getParameter("emailComercial", request);
			String emailPessoal = getParameter("emailPessoal", request);
			String cpf = getParameter("cpf", request);
			String cep = getParameter("cep", request);
			String rua = getParameter("rua", request);
			String numero = getParameter("numero", request);
			String bairro = getParameter("bairro", request);
			String complemento = getParameter("complemento", request);
			String foneFixo = getParameter("foneFixo", request);
			String celularComercial = getParameter("celularComercial", request);
			String celularPessoal = getParameter("celularPessoal", request);
			String planoId = getParameter("planoId", request);
			String valorPlano = getParameter("valorCliente", request);
			String cnpjCpf = getParameter("cnpjCpf", request);
			String clienteBene = getParameter("clienteBene", request);

			Cliente cliente = new Cliente();
			cliente.setNome(nome);
			cliente.setEmailComercial(emailComercial);
			cliente.setEmailPessoal(emailPessoal);
			cliente.setCpf(cpf);
			cliente.setCep(cep);
			cliente.setRua(rua);
			cliente.setNumero(numero);
			cliente.setBairro(bairro);
			cliente.setComplemento(complemento);
			cliente.setFonefixo(foneFixo);
			if (celularComercial != null && !celularComercial.trim().equals("")) {
				cliente.setCelularComercial(celularComercial);
			}
			if (celularPessoal != null && !celularPessoal.trim().equals("")) {
				cliente.setCelularPessoal(celularPessoal);
			}
			Plano plano = planoService.findPlanoById(Integer.valueOf(planoId));

			cliente.setPlano(plano);
			BigDecimal valor = new BigDecimal(valorPlano);
			cliente.setValor(valor);

			cliente.setEhCpfCnpj(isCPF(cnpjCpf));

			cliente.setEhCliente(isCliente(clienteBene));

			clienteService.save(cliente);
			myObj.addProperty("success", Boolean.TRUE);

		} catch (Exception e) {
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na inserção do Cliente. Verificar logs servidor");
			e.printStackTrace();

		} finally {
			System.out.println("json = " + myObj.toString());
			out.println(myObj.toString());
			out.close();

		}

	}

	public void procurar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomeCliente = getParameter("nomeCliente", request);
		String cpfCliente = getParameter("cpfCliente", request);
		String clienteBene = getParameter("clienteBene", request);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Cliente cliente = new Cliente();
			cliente.setNome(nomeCliente);
			cliente.setCpf(cpfCliente);
			if (!"all".equalsIgnoreCase(clienteBene)) {
				cliente.setEhCliente(isCliente(clienteBene));
			}

			List<Cliente> list = clienteService.findClienteByParameter(cliente);

			response.setContentType("text/json");
			Gson gson = new Gson();
			String listClientes = gson.toJson(list);
			System.out.println("Clientes = " + listClientes);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listClientes);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Cliente. Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}

	public void procurarCliente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = getParameter("id", request);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Integer id = null;
			if (idStr != null && !"".equals(idStr.trim())) {
				id = Integer.valueOf(idStr);
			}

			Cliente cliente = clienteService.findClienteById(id);

			response.setContentType("text/json");
			Gson gson = new Gson();
			String clienteJson = gson.toJson(cliente);
			System.out.println("Cliente = " + clienteJson);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("cliente", clienteJson);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Cliente.Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}

	public void deletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			String idStr = getParameter("idCliente", request);

			Cliente cliente = new Cliente();
			Integer id = null;
			if (idStr != null && !"".equals(idStr.trim())) {
				id = Integer.valueOf(idStr);
			}
			cliente.setId(id);

			clienteService.delete(cliente);
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

	public void alterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			String idStr = getParameter("idCliente", request);
			String nome = getParameter("nome", request);
			String emailComercial = getParameter("emailComercial", request);
			String emailPessoal = getParameter("emailPessoal", request);
			String cpf = getParameter("cpf", request);
			String cep = getParameter("cep", request);
			String rua = getParameter("rua", request);
			String numero = getParameter("numero", request);
			String bairro = getParameter("bairro", request);
			String complemento = getParameter("complemento", request);
			String foneFixo = getParameter("foneFixo", request);
			String celularComercial = getParameter("celularComercial", request);
			String celularPessoal = getParameter("celularPessoal", request);
			String planoId = getParameter("planoId", request);
			String valorPlano = getParameter("valorCliente", request);
			String cnpjCpf = getParameter("cnpjCpf", request);
			String clienteBene = getParameter("clienteBene", request);
			Integer id = null;
			if (idStr != null && !"".equals(idStr.trim())) {
				id = Integer.valueOf(idStr);
			}

			Cliente cliente = new Cliente();
			cliente.setId(id);
			cliente.setNome(nome);
			cliente.setEmailComercial(emailComercial);
			cliente.setEmailPessoal(emailPessoal);
			cliente.setCpf(cpf);
			cliente.setCep(cep);
			cliente.setRua(rua);
			cliente.setNumero(numero);
			cliente.setBairro(bairro);
			cliente.setComplemento(complemento);
			cliente.setFonefixo(foneFixo);
			if (celularComercial != null && !celularComercial.trim().equals("")) {
				cliente.setCelularComercial(celularComercial);
			}
			if (celularPessoal != null && !celularPessoal.trim().equals("")) {
				cliente.setCelularPessoal(celularPessoal);
			}
			cliente.setEhCpfCnpj(isCPF(cnpjCpf));
			cliente.setEhCliente(isCliente(clienteBene));
			Plano plano = planoService.findPlanoById(Integer.valueOf(planoId));

			cliente.setPlano(plano);
			BigDecimal valor = new BigDecimal(valorPlano);
			cliente.setValor(valor);

			clienteService.update(cliente);
			response.setContentType("text/json");

			myObj.addProperty("success", Boolean.TRUE);
			System.out.println("json = " + myObj.toString());

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Alteração do Cliente. Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();

		}
	}

	public void obterTodosBeneficiarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Cliente cliente = new Cliente();
			cliente.setEhCliente(false);
			List<Cliente> list = clienteService.findClienteByParameter(cliente);

			response.setContentType("text/json");
			Gson gson = new Gson();
			String listClientes = gson.toJson(list);
			System.out.println("Clientes = " + listClientes);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listClientes);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Cliente. Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}

	@Override
	public Class<ClienteView> getMethodClass() {
		return ClienteView.class;
	}

}
