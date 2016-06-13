package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.filters.FiltroAnuncio;
import org.freelasearch.services.ServicoAnunciante;
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
			ServicoAnunciante servicoAnunciante = new ServicoAnunciante();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			// BUSCAR
			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				FiltroAnuncio filtro = new FiltroAnuncio();

				if (request.getParameter("id") != null) {
					filtro.setIdAnuncio(Integer.valueOf(request.getParameter("id")));
				} else {
					filtro.setQtdRetorno(request.getParameter("qtdRetorno") == null ? 0 : Integer.valueOf(request.getParameter("qtdRetorno")));
					filtro.setQtdExibida(request.getParameter("qtdExibida") == null ? 0 : Integer.valueOf(request.getParameter("qtdExibida")));
					filtro.setIdPrimeiroLista(request.getParameter("idPrimeiroLista") == null ? 0 : Integer.valueOf(request.getParameter("idPrimeiroLista")));
					filtro.setTipoBusca(request.getParameter("tipoBusca") == null ? null : Integer.valueOf(request.getParameter("tipoBusca")));
					filtro.setIdUsuario(request.getParameter("idUsuario") == null ? null : Integer.valueOf(request.getParameter("idUsuario")));
				}
				oos.writeObject(servico.buscarLista(filtro));
			}
			// SALVAR (CADASTRAR / ATUALIZAR)
			else if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoAnuncio dto = (DtoAnuncio) ois.readObject();

				// Como o Anúncio possui Anunciante, é preciso preencher esse Objeto por completo.
				dto = servico.salvar(dto);
				dto.setAnunciante(servicoAnunciante.buscarPorId(dto.getAnunciante().getId()));

				oos.writeObject(dto);
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
