package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.filters.FiltroAnunciante;
import org.freelasearch.services.ServicoAnunciante;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/anunciante/*")
public class AnuncianteServlet extends HttpServlet {

	private static final long serialVersionUID = -3722383587256043652L;

	public AnuncianteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoAnunciante servico = new ServicoAnunciante();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			// BUSCAR
			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				FiltroAnunciante filtro = new FiltroAnunciante();

				if (request.getParameter("id") != null) {
					filtro.setIdAnunciante(Integer.valueOf(request.getParameter("id")));
				} else {
					filtro.setEmail(request.getParameter("email"));
				}

				oos.writeObject(servico.buscarLista(filtro));
			}
			// SALVAR (CADASTRAR / ATUALIZAR)
			else if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoAnunciante dto = (DtoAnunciante) ois.readObject();

				servico.salvar(dto);

				oos.writeObject(new Boolean(true));
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
