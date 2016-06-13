package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.filters.FiltroFreelancer;
import org.freelasearch.services.ServicoFreelancer;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/freelancer/*")
public class FreelancerServlet extends HttpServlet {

	private static final long serialVersionUID = 1881742752001896996L;

	public FreelancerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoFreelancer servico = new ServicoFreelancer();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			// BUSCAR
			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				FiltroFreelancer filtro = new FiltroFreelancer();

				if (request.getParameter("id") != null) {
					filtro.setIdFreelancer(Integer.valueOf(request.getParameter("id")));
				} else {
					filtro.setIdUsuario(Integer.valueOf(request.getParameter("idUsuario")));
					filtro.setEmail(request.getParameter("email"));
				}

				oos.writeObject(servico.buscarLista(filtro));
			}
			// SALVAR (CADASTRAR / ATUALIZAR)
			else if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoFreelancer dto = (DtoFreelancer) ois.readObject();

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
