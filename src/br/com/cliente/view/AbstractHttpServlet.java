package br.com.cliente.view;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public abstract class AbstractHttpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract Class getMethodClass();

	public void init(ServletConfig config) {
		try {
			super.init(config);
			SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String metodo = request.getParameter("method");
		try {
			Method methodCall = getMethodClass().getMethod(metodo, HttpServletRequest.class, HttpServletResponse.class);
			methodCall.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getParameter(String name, HttpServletRequest request) {
		String valor = request.getParameter(name);
		System.out.println("Name = " + name + " -> Valor = " + valor);
		return valor;
	}
	
	
	

}
