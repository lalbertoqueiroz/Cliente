package br.com.cliente.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.cliente.modelo.Cliente;
import br.com.cliente.modelo.Parcela;
import br.com.cliente.modelo.ParcelaAndFatura;
import br.com.cliente.service.ClienteService;
import br.com.cliente.service.PagamentoService;
import br.com.cliente.util.DateUtil;

@WebServlet("/faturamentoView")
public class FaturamentoView extends AbstractHttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	public ClienteService clienteService;
	
	
	@Autowired
	public PagamentoService pagamentoService;

	@Override
	public Class<FaturamentoView> getMethodClass() {
		return FaturamentoView.class;
	}
	
	
	public void obterClientes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Cliente clienteVO  = new Cliente();
			clienteVO.setEhCliente(true);
			List<Cliente> list = clienteService.findClienteByParameter(clienteVO);

			response.setContentType("text/json");
			Gson gson = new Gson();
			String listClientes = gson.toJson(list);
			System.out.println("Clientes = " + listClientes);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listClientes);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na obtenção das clientes. Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}
	
	
	public void pesquisar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String dataInicial = getParameter("dataInicial", request);
		String dataFinal = getParameter("dataFinal", request);
		String clienteId = getParameter("clienteId", request);
		
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			Date startDate = DateUtil.parse(dataInicial, null);
			Date endDate = null;
			if (dataFinal != null && !"".equals(dataFinal)) {
				endDate = DateUtil.parse(dataFinal, null);
			} 

			Cliente clienteVO  = new Cliente();
			clienteVO.setEhCliente(true);
			if(clienteId != null){
				clienteVO.setId(Integer.valueOf(clienteId));	
			}
			
			List<ParcelaAndFatura> list = pagamentoService.procurarParcelasAndFaturaByCliente(clienteVO, startDate, endDate);

			response.setContentType("text/json");
			Gson gson = new Gson();
			String listParcelas = gson.toJson(list);
			System.out.println("Parcelas = " + listParcelas);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listParcelas);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na obtenção das parcelas. Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}
	}
	
	
	public void procurarParcela(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = getParameter("id", request);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Integer id = null;
			if (idStr != null && !"".equals(idStr.trim())) {
				id = Integer.valueOf(idStr);
			}

			Parcela parcela = pagamentoService.procurarParcelasById(id);

			response.setContentType("text/json");
			Gson gson = new Gson();
			String parcelaJson = gson.toJson(parcela);
			System.out.println("parcela = " + parcelaJson);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("parcela", parcelaJson);

		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Parcela .Verificar logs servidor");
		} finally {
			out.println(myObj.toString());
			out.close();
		}

	}
}
