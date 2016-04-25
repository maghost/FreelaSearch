package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.entities.Usuario;
import org.freelasearch.services.ServicoUsuario;
import org.freelasearch.utils.ExceptionFreelaSearch;

@WebServlet("/usuario/*")
public class UsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = -1072476343357351532L;

	public UsuarioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServicoUsuario servico = new ServicoUsuario();
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());

			// SALVAR
			if (request.getRequestURI().toLowerCase().endsWith("/salvar")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoUsuario dto = (DtoUsuario) ois.readObject();

				Usuario usuario = servico.montarBean(dto);
				servico.salvar(usuario);

				oos.writeObject(new Boolean(true));
			}

			// LOGIN
			if (request.getRequestURI().toLowerCase().endsWith("/login")) {
				String email = request.getParameter("email");
				String senha = request.getParameter("senha");
				oos.writeObject(servico.login(email, senha));
			}
		} catch (ExceptionFreelaSearch e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

}
