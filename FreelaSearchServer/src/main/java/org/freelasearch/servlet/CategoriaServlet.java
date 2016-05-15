package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.filters.FiltroCategoria;
import org.freelasearch.services.ServicoCategoria;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/categoria/*")
public class CategoriaServlet extends HttpServlet {

	private static final long serialVersionUID = -1072476343357351532L;

	public CategoriaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoCategoria servico = new ServicoCategoria();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				// Se tiver id, busca preenchendo apenas esse campo, caso contrário, preenche o filtro
				if (request.getParameter("id") != null) {
					FiltroCategoria filtro = new FiltroCategoria();
					filtro.setId(Integer.valueOf(request.getParameter("id")));

					oos.writeObject(servico.buscarPorId(filtro));
				} else {
					oos.writeObject(servico.buscarLista());
				}
			}
		} catch (ExceptionFreelaSearch e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
