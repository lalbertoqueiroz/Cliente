package br.com.cliente.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import br.com.cliente.modelo.Cliente;
import br.com.cliente.modelo.ClienteBoleto;
import br.com.cliente.modelo.Conta;
import br.com.cliente.modelo.DadosComplementar;
import br.com.cliente.modelo.Fatura;
import br.com.cliente.modelo.Pagamento;
import br.com.cliente.modelo.Parcela;
import br.com.cliente.service.ClienteService;
import br.com.cliente.service.ContaService;
import br.com.cliente.service.PagamentoService;
import br.com.cliente.util.DateUtil;

@WebServlet("/emitirBoletoView")

public class EmitirBoleto extends AbstractHttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ClienteService clienteService;

	@Autowired
	public ContaService contaService;

	@Autowired
	public PagamentoService pagamentoService;

	@Override
	public Class<EmitirBoleto> getMethodClass() {
		return EmitirBoleto.class;
	}
	
	
	

	public void downloadPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String pathFile = getParameter("pathFile", request);

		try {
			
			File downloadFile = new File(pathFile);
	        FileInputStream inStream = new FileInputStream(downloadFile);
	        
	        // modifies response
	        response.setContentType("application/pdf");
	        
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	        headerKey = "Cache-control";
	        headerValue = "must-revalidate, post-check=0, pre-check=0";
	        response.setHeader(headerKey, headerValue);
	        response.setHeader("Content-Transfer-Encoding", "binary");
	        
	         
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        
	        response.setContentLength((int)downloadFile.length());
	         
	        inStream.close();
	        outStream.close();
	        response.flushBuffer();
	       
	       
	    } catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private Boolean isCliente(String clienteBene) {
		return clienteBene != null && clienteBene.equalsIgnoreCase("cliente");
	}

	public void pesquisarClienteAndFatura(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Cliente> list = procurarCliente(request, response);
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
		List<ClienteBoleto> listClienteBoletos = null;
		try {
			if (list != null) {
				listClienteBoletos = new ArrayList<ClienteBoleto>();
				for (Cliente cliente : list) {
					ClienteBoleto clienteBoleto = new ClienteBoleto();
					clienteBoleto.setIdCliente(cliente.getId());
					clienteBoleto.setNome(cliente.getNome());
					clienteBoleto.setPlano(cliente.getPlano().getNome());
					clienteBoleto.setValorParcela(cliente.getPlano().getValor());
					Fatura fatura = pagamentoService.procurarFaturaByCliente(cliente);
					if (fatura != null) {
						clienteBoleto.setDataGeracao(fatura.getDataGeracao());
						clienteBoleto.setValorFatura(fatura.getValorFatura());
						clienteBoleto.setQtsParcelas(fatura.getQtdParcelas());
						clienteBoleto.setFatura(fatura.getPathFatura());
					}
					listClienteBoletos.add(clienteBoleto);
				}
			}
			response.setContentType("text/json");
			Gson gson = new Gson();
			String listBoletos = gson.toJson(listClienteBoletos);
			System.out.println("Clientes = " + listBoletos);

			myObj.addProperty("success", Boolean.TRUE);
			myObj.addProperty("list", listBoletos);
		} catch (Exception e) {
			myObj.addProperty("success", Boolean.FALSE);
			myObj.addProperty("message", "Problema na inserção do Cliente. Verificar logs servidor");
			e.printStackTrace();

		} finally {
			out.println(myObj.toString());
			out.close();
		}
	}

	private List<Cliente> procurarCliente(HttpServletRequest request, HttpServletResponse response) {
		String nomeCliente = getParameter("nomeCliente", request);
		String cpfCliente = getParameter("cpfCliente", request);
		String clienteBene = getParameter("clienteBene", request);
		List<Cliente> list = null;
		try {

			Cliente cliente = new Cliente();
			cliente.setNome(nomeCliente);
			cliente.setCpf(cpfCliente);
			if (!"all".equalsIgnoreCase(clienteBene)) {
				cliente.setEhCliente(isCliente(clienteBene));
			}
			list = clienteService.findClienteByParameter(cliente);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return list;
	}

	public void gerarBoleto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idClientes = request.getParameter("ids");
		String diaVencimento = getParameter("diaVencimento", request);
		String valor = getParameter("valor", request);
		String mesInicial = getParameter("mesInicial", request);
		String mesFinal = getParameter("mesFinal", request);
		String contaId = getParameter("contaId", request);
		try {

			Date startDate = DateUtil.parse(diaVencimento + "/" + mesInicial, null);
			Date endDate = null;
			if (mesFinal == null ||  "".equals(mesFinal)) {
				endDate = DateUtil.parse(diaVencimento + "/" + mesInicial, null);
			} else {
				endDate = DateUtil.parse(diaVencimento + "/" + mesFinal, null);
			}

			int parcelas = DateUtil.getParcelas(startDate, endDate);
			Integer id = null;
			Conta conta = contaService.findContaById(Integer.valueOf(contaId));
			Cliente beneficiario = conta.getIdBeneficiario();
			List<Pagamento> listPagamento = new ArrayList<>();
			Date dataGeracao = new Date();
			if (idClientes != null) {
				String ids[] = idClientes.split(",");
				BigDecimal valorFatura = BigDecimal.ZERO;
				for (int i = 0; i < ids.length; i++) {
					if (ids[i] != null && !"".equals(ids[i])) {
						Pagamento pagamento = new Pagamento();
						Fatura fatura = new Fatura();
						id = Integer.valueOf(ids[i]);
						Cliente cliente = clienteService.findClienteById(id);
						if (valor != null && !"".equals(valor.trim())) {
							cliente.setValor(new BigDecimal(valor));
						}
						valorFatura = valorFatura.add(cliente.getValor());
						List<Boleto> boletos = new ArrayList<Boleto>();
						List<Parcela> listParcelas = new ArrayList<>();
						for (int j = 0; j <= parcelas; j++) {
							Parcela parcela = new Parcela();
							Calendar calendarDtVen = Calendar.getInstance();
							calendarDtVen.setTime(startDate);
							calendarDtVen.add(Calendar.MONTH, j);
							Date dateVencimento = calendarDtVen.getTime();
							DadosComplementar complementar = new DadosComplementar();
							complementar.setDateGeracao(dataGeracao);
							complementar.setDateVencimento(dateVencimento);
							Boleto boleto = criarBoleto(cliente, beneficiario, conta, complementar);
							boletos.add(boleto);

							parcela.setCliente(cliente);
							parcela.setDataGeracao(dataGeracao);
							parcela.setDataVencimento(dateVencimento);
							parcela.setDesprezarParcela(Boolean.FALSE);
							parcela.setValorAPagar(cliente.getValor());
							listParcelas.add(parcela);
						}
						fatura.setCliente(cliente);
						fatura.setDataGeracao(dataGeracao);

						String datetime = DateUtil.formatDate(new Date(), "ddMMyyyy");
						String nameFile = cliente.getNome().toLowerCase().trim().replace(" ", "") + datetime + ".pdf";

						System.out.println("Path " + getServletContext().getContextPath());

						// get absolute path of the application
						ServletContext context = request.getServletContext();
						String appPath = context.getRealPath("");
						System.out.println("appPath = " + appPath);
						String fullPath = "C:\\Lalberto\\Leo\\pdf\\" + datetime + "\\" + nameFile;
						File downloadFile = new File(fullPath);
						// downloadFile.deleteOnExit();
						downloadFile.getParentFile().mkdirs();
						System.out.println("downloadFile.getAbsolutePath() = " + downloadFile.getAbsolutePath());
						fatura.setPathFatura(downloadFile.getAbsolutePath());
						fatura.setQtdParcelas(listParcelas.size());
						fatura.setValorFatura(valorFatura);
						pagamento.setFatura(fatura);
						pagamento.setListPacerla(listParcelas);

						ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
						InputStream stream = classLoader
								.getResourceAsStream("META-INF/templates/BoletoCarne3PorPagina.pdf");

						if (stream != null) {
							System.out.println("InputStream " + stream);
						}

						gerarBoleto(boletos, downloadFile.getAbsolutePath(), stream);
						listPagamento.add(pagamento);
					}
				}
				pagamentoService.saveParcelasAndFatura(listPagamento);
				pesquisarClienteAndFatura(request, response);
			} else {
				System.out.println("Selecionar Cliente");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

	}

	public Boleto criarBoleto(Cliente cliente, Cliente beneficiario, Conta conta, DadosComplementar complementar) {

		Cedente cedente = criarCedente(cliente, beneficiario);
		Sacado sacado = criarSacador(cliente);
		ContaBancaria contaBancaria = criarContaBancaria(conta);
		Titulo titulo = criarTitulo(cliente, conta, contaBancaria, sacado, cedente, complementar);

		Boleto boleto = new Boleto(titulo);

		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em " + "qualquer Banco até o Vencimento.");
		boleto.setInstrucaoAoSacado(
				"Senhor sacado, sabemos sim que o valor " + "cobrado não é o esperado, aproveite o DESCONTÃO!");
		boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");

		return boleto;

	}

	public Cedente criarCedente(Cliente cliente, Cliente beneficiario) {
		Cedente cedente = new Cedente(beneficiario.getNome(), beneficiario.getCpf());
		return cedente;
	}

	public Sacado criarSacador(Cliente cliente) {
		Sacado sacado = new Sacado(cliente.getNome(), cliente.getCpf());
		Endereco enderecoSac = new Endereco();
		enderecoSac.setUF(UnidadeFederativa.CE);
		enderecoSac.setLocalidade("Fortaleza");
		enderecoSac.setCep(cliente.getCep());
		enderecoSac.setBairro(cliente.getBairro());
		enderecoSac.setLogradouro(cliente.getRua());
		enderecoSac.setNumero(cliente.getNumero());
		sacado.addEndereco(enderecoSac);
		return sacado;
	}

	public ContaBancaria criarContaBancaria(Conta conta) {
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_SANTANDER.create());
		contaBancaria.setNumeroDaConta(new NumeroDaConta(Integer.valueOf(conta.getCodConta()), "0"));
		contaBancaria.setCarteira(new Carteira(conta.getCodCarteira()));
		contaBancaria.setAgencia(new Agencia(Integer.valueOf(conta.getCodAgencia()), "1"));
		return contaBancaria;
	}

	private Titulo criarTitulo(Cliente cliente, Conta conta, ContaBancaria contaBancaria, Sacado sacado,
			Cedente cedente, DadosComplementar complementar) {
		Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("123456");
		titulo.setNossoNumero("99345678912");
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(cliente.getValor());
		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(complementar.getDateVencimento());
		titulo.setTipoDeDocumento(TipoDeTitulo.valueOfSigla(conta.getTipTitulo()));
		titulo.setAceite(Aceite.valueOf(conta.getAceite() ? "S" : "N"));
		titulo.setDesconto(new BigDecimal(0.05));
		titulo.setDeducao(BigDecimal.ZERO);
		titulo.setMora(BigDecimal.ZERO);
		titulo.setAcrecimo(BigDecimal.ZERO);
		titulo.setValorCobrado(BigDecimal.ZERO);

		return titulo;
	}

	private void gerarBoleto(List<Boleto> boletos, String filePath, InputStream templatePersonalizado) {
		BoletoViewer boletoViewer = new BoletoViewer(boletos.get(0));
		boletoViewer.setTemplate(templatePersonalizado);

		List<byte[]> boletosEmBytes = new ArrayList<byte[]>(boletos.size());

		// Adicionando os PDF, em forma de array de bytes, na lista.
		for (Boleto bop : boletos) {
			boletosEmBytes.add(boletoViewer.setBoleto(bop).getPdfAsByteArray());
		}

		try {

			// Criando o arquivo com os boletos da lista
			Files.bytesToFile(filePath, mergeFilesInPages(boletosEmBytes));

		} catch (Exception e) {
			throw new IllegalStateException("Erro durante geração do PDF! Causado por " + e.getLocalizedMessage(), e);
		}
	}

	public static byte[] mergeFilesInPages(List<byte[]> pdfFilesAsByteArray) throws DocumentException, IOException {

		Document document = new Document();
		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();

		PdfWriter writer = PdfWriter.getInstance(document, byteOS);

		document.open();

		PdfContentByte cb = writer.getDirectContent();
		float positionAnterior = 0;

		// Para cada arquivo da lista, cria-se um PdfReader, responsável por ler
		// o arquivo PDF e recuperar informações dele.
		for (byte[] pdfFile : pdfFilesAsByteArray) {

			PdfReader reader = new PdfReader(pdfFile);

			// Faz o processo de mesclagem por página do arquivo, começando pela
			// de número 1.
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {

				float documentHeight = cb.getPdfDocument().getPageSize().getHeight();

				// Importa a página do PDF de origem
				PdfImportedPage page = writer.getImportedPage(reader, i);
				float pagePosition = positionAnterior;

				/*
				 * Se a altura restante no documento de destino form menor que a
				 * altura do documento, cria-se uma nova página no documento de
				 * destino.
				 */
				if ((documentHeight - positionAnterior) <= page.getHeight()) {
					document.newPage();
					pagePosition = 0;
					positionAnterior = 0;
				}

				// Adiciona a página ao PDF destino
				cb.addTemplate(page, 0, pagePosition);

				positionAnterior += page.getHeight();
			}
		}

		byteOS.flush();
		document.close();

		byte[] arquivoEmBytes = byteOS.toByteArray();
		byteOS.close();

		return arquivoEmBytes;
	}

}
