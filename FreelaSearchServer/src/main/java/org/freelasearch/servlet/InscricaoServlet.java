package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.filters.FiltroInscricao;
import org.freelasearch.services.ServicoInscricao;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/inscricao/*")
public class InscricaoServlet extends HttpServlet {

	private static final long serialVersionUID = -8003446558351125883L;

	public InscricaoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoInscricao servico = new ServicoInscricao();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			// BUSCAR
			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				FiltroInscricao filtro = new FiltroInscricao();

				if (request.getParameter("id") != null) {
					filtro.setIdInscricao(Integer.valueOf(request.getParameter("id")));
				} else {
					filtro.setQtdRetorno(request.getParameter("qtdRetorno") == null ? 0 : Integer.valueOf(request.getParameter("qtdRetorno")));
					filtro.setQtdExibida(request.getParameter("qtdExibida") == null ? 0 : Integer.valueOf(request.getParameter("qtdExibida")));
					filtro.setIdPrimeiroLista(request.getParameter("idPrimeiroLista") == null ? 0 : Integer.valueOf(request.getParameter("idPrimeiroLista")));
					filtro.setTipoBusca(request.getParameter("tipoBusca") == null ? null : Integer.valueOf(request.getParameter("tipoBusca")));
					filtro.setIdUsuario(request.getParameter("idUsuario") == null ? null : Integer.valueOf(request.getParameter("idUsuario")));
				}
				oos.writeObject(servico.buscarLista(filtro));
			}
			// SALVAR (CADASTRAR)
			else if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoInscricao dto = (DtoInscricao) ois.readObject();

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
