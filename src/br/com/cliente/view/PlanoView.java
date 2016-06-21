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

import br.com.cliente.modelo.Plano;
import br.com.cliente.service.PlanoService;

@WebServlet("/planoView")
public class PlanoView extends AbstractHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	public PlanoService planoService;

	public void salvarPlano(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/json");
		JsonObject myObj = new JsonObject();
		try {
			String nome = getParameter("nomePlano", request);
			String valorStrg = getParameter("valor", request);

			Plano plano = new Plano();

			BigDecimal valor = new BigDecimal(valorStrg);

			plano.setNome(nome);
			plano.setValor(valor);

			planoService.save(plano);
			
			
			myObj.addProperty("success", Boolean.TRUE);
		} catch (Exception e) {
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na inserção do Plano.Verificar logs servidor" );
			e.printStackTrace();
			
		}finally {
			System.out.println("json = " + myObj.toString());
			out.println(myObj.toString());
			out.close();
			
		}

	}

	public void procurar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = getParameter("nome", request);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Plano plano = new Plano();
			plano.setNome(nome);

			List<Plano> list =  planoService.findPlanoByParameter(plano);

			response.setContentType("text/json");
			Gson gson = new Gson();
	        String listPlanos = gson.toJson(list);
	        System.out.println("planos = " + listPlanos);
			
			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listPlanos);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Plano.Verificar logs servidor" );
		}finally {
			out.println(myObj.toString());
			out.close();
		}

	}
	
	public void procurarPlano(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = getParameter("id", request);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {

			Integer id = null;
			if(idStr != null && !"".equals(idStr.trim())){
				id = Integer.valueOf(idStr);
			}

			Plano plano =  planoService.findPlanoById(id);

			response.setContentType("text/json");
			Gson gson = new Gson();
	        String planoJson = gson.toJson(plano);
	        System.out.println("plano = " + planoJson);
			
			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("plano", planoJson);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Plano.Verificar logs servidor" );
		}finally {
			out.println(myObj.toString());
			out.close();
		}

	}
	
	public void deletar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			String idStr = getParameter("idPlano", request);
			Plano plano = new Plano();
			Integer id = null;
			if(idStr != null && !"".equals(idStr.trim())){
				id = Integer.valueOf(idStr);
			}
			plano.setId(id);

			planoService.delete(plano);
			response.setContentType("text/json");
			
			myObj.addProperty("success", Boolean.TRUE);
			System.out.println("json = " + myObj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Remoção do Plano.Verificar logs servidor" );
			
		}finally {
			out.println(myObj.toString());
			out.close();
			
		}

	}
	
	public void alterar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			String idStr = getParameter("idPlano", request);
			String nome = getParameter("nomePlano", request);
			String valorStrg = getParameter("valor", request);
			
			Plano plano = new Plano();
			BigDecimal valor = new BigDecimal(valorStrg);
			
			Integer id = null;
			if(idStr != null && !"".equals(idStr.trim())){
				id = Integer.valueOf(idStr);
			}
			plano.setId(id);
			plano.setNome(nome);
			plano.setValor(valor);

			planoService.update(plano);
			response.setContentType("text/json");
			
			myObj.addProperty("success", Boolean.TRUE);
			System.out.println("json = " + myObj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Alteração do Plano.Verificar logs servidor" );
		}finally {
			out.println(myObj.toString());
			out.close();
			
		}
	}
	
	
	public void obterTodos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		try {
			List<Plano> list =  planoService.findAll();
			response.setContentType("text/json");
			Gson gson = new Gson();
	        String listPlanos = gson.toJson(list);
	        System.out.println("planos = " + listPlanos);
			
			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listPlanos);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na Edição do Plano.Verificar logs servidor" );
		}finally {
			out.println(myObj.toString());
			out.close();
		}

	}



	@Override
	public Class getMethodClass() {
		return PlanoView.class;
	}

}
