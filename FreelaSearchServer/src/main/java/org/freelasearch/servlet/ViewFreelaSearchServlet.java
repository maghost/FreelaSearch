package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.services.ServicoViewFreelaSearch;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/view/*")
public class ViewFreelaSearchServlet extends HttpServlet {

	private static final long serialVersionUID = -8662307196490301833L;

	public ViewFreelaSearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoViewFreelaSearch servico = new ServicoViewFreelaSearch();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				oos.writeObject(servico.buscar());
			}
		} catch (ExceptionFreelaSearch e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
