package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.entities.Categoria;
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
				Integer id = Integer.valueOf(request.getParameter("id"));
				oos.writeObject(servico.montarDto(id));
			} else if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoCategoria dto = (DtoCategoria) ois.readObject();

				Categoria categoria = servico.montarBean(dto);
				servico.salvar(categoria);

				oos.writeObject(new Boolean(true));
			}
		} catch (ExceptionFreelaSearch e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
