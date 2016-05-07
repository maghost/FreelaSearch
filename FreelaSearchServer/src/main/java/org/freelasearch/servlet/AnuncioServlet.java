package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.services.ServicoAnuncio;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/anuncio/*")
public class AnuncioServlet extends HttpServlet {

	private static final long serialVersionUID = -8003446558351125883L;

	public AnuncioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoAnuncio servico = new ServicoAnuncio();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				Integer qtdRetorno = request.getParameter("qtdRetorno") == null ? 0 : Integer.valueOf(request.getParameter("qtdRetorno"));
				Integer qtdExibida = request.getParameter("qtdExibida") == null ? 0 : Integer.valueOf(request.getParameter("qtdExibida"));
				Integer tipoBusca = request.getParameter("tipoBusca") == null ? null : Integer.valueOf(request.getParameter("tipoBusca"));
					
				System.out.println("qtdRetorno: " + qtdRetorno);
				System.out.println("qtdExibida: " + qtdExibida);
				System.out.println("tipoBusca: " + tipoBusca);
				
				oos.writeObject(servico.buscarLista(qtdRetorno, qtdExibida, tipoBusca));
			}
		} catch (ExceptionFreelaSearch e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
