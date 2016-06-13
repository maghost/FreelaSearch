package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.filters.FiltroContratacao;
import org.freelasearch.services.ServicoContratacao;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/contratacao/*")
public class ContratacaoServlet extends HttpServlet {

	private static final long serialVersionUID = -2291122431747201961L;

	public ContratacaoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoContratacao servico = new ServicoContratacao();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			// BUSCAR
			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				FiltroContratacao filtro = new FiltroContratacao();

				if (request.getParameter("id") != null) {
					filtro.setIdContratacao(Integer.valueOf(request.getParameter("id")));
				} else {
					filtro.setQtdRetorno(request.getParameter("qtdRetorno") == null ? 0 : Integer.valueOf(request.getParameter("qtdRetorno")));
					filtro.setQtdExibida(request.getParameter("qtdExibida") == null ? 0 : Integer.valueOf(request.getParameter("qtdExibida")));
					filtro.setIdPrimeiroLista(request.getParameter("idPrimeiroLista") == null ? 0 : Integer.valueOf(request.getParameter("idPrimeiroLista")));
					filtro.setIdFreelancer(request.getParameter("idFreelancer") == null ? null : Integer.valueOf(request.getParameter("idFreelancer")));
					filtro.setIdAnunciante(request.getParameter("idAnunciante") == null ? null : Integer.valueOf(request.getParameter("idAnunciante")));
				}
				oos.writeObject(servico.buscarLista(filtro));
			}
			// SALVAR (CADASTRAR)
			else if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoContratacao dto = (DtoContratacao) ois.readObject();

				oos.writeObject(servico.salvar(dto));
			}
		} catch (ExceptionFreelaSearch e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
}
