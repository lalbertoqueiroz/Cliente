 package br.com.cliente.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;



/**
 * Servlet implementation class ClienteControle
 */
@WebServlet("/ClienteControle")
public class ClienteControle extends AbstractHttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClienteControle() {
        super();
        
    }
    
    @SuppressWarnings("rawtypes")
	@Override
	public Class getMethodClass() {
		return ClienteControle.class;
	}
    
    public void inserirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String nomeCliente = getParameter("nomeCliente", request);
    	String emailPessoal = getParameter("emailPessoal", request);
    	String emailComercial = getParameter("emailComercial", request); 
    	String cpf = getParameter("cpf", request);
    	String cep = getParameter("cep", request);
    	String rua = getParameter("rua", request);
    	String numero = getParameter("numero", request);
    	String bairro = getParameter("bairro", request);
    	String complemento = getParameter("complemento", request);
    	String fixo = getParameter("fixo", request);
    	String celular1 = getParameter("celular1", request);
    	String celular2 = getParameter("celular2", request);
    	String planos = getParameter("planos", request);
    	String preco = getParameter("preco", request);
    	 
    	PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        JsonObject myObj = new JsonObject();
		myObj.addProperty("sucess", Boolean.TRUE);
		out.println(myObj.toString());
        out.close();
    	 
    }

		
	public void carregarPlano(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
	}

	

}
