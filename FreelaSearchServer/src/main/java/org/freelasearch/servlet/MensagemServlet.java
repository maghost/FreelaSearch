package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.filters.FiltroMensagem;
import org.freelasearch.services.ServicoMensagem;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/mensagem/*")
public class MensagemServlet extends HttpServlet {

	private static final long serialVersionUID = -3293394499245043231L;

	public MensagemServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoMensagem servico = new ServicoMensagem();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			// BUSCAR
			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				FiltroMensagem filtro = new FiltroMensagem();

				if (request.getParameter("id") != null) {
					filtro.setIdMensagem(Integer.valueOf(request.getParameter("id")));
				} else {
					filtro.setQtdRetorno(request.getParameter("qtdRetorno") == null ? 0 : Integer.valueOf(request.getParameter("qtdRetorno")));
					filtro.setQtdExibida(request.getParameter("qtdExibida") == null ? 0 : Integer.valueOf(request.getParameter("qtdExibida")));
					filtro.setIdPrimeiroLista(request.getParameter("idPrimeiroLista") == null ? 0 : Integer.valueOf(request.getParameter("idPrimeiroLista")));
					filtro.setConteudo(request.getParameter("conteudo") == null ? null : request.getParameter("conteudo"));
					filtro.setData(request.getParameter("data") == null ? null : request.getParameter("data"));
					filtro.setIdContraparte(request.getParameter("idContraparte") == null ? null : Integer.valueOf(request.getParameter("idContraparte")));
					filtro.setIdUsuario(request.getParameter("idUsuario") == null ? null : Integer.valueOf(request.getParameter("idUsuario")));
				}
				oos.writeObject(servico.buscarLista(filtro));
			}
			// SALVAR (CADASTRAR)
			else if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoMensagem dto = (DtoMensagem) ois.readObject();

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
