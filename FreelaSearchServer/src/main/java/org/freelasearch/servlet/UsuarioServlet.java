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
import org.freelasearch.filters.FiltroUsuario;
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

				// Salva o usuário e passa os dados atualizados para o dto
				// Retorna o objeto preenchido para a aplicação
				oos.writeObject(servico.salvar(dto));
			}

			// LOGIN
			if (request.getRequestURI().toLowerCase().endsWith("/login")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoUsuario dto = (DtoUsuario) ois.readObject();

				oos.writeObject(servico.login(dto));
			}

			// LOGIN/REGISTRO FACEBOOK
			if (request.getRequestURI().toLowerCase().endsWith("/facebook")) {
				ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
				DtoUsuario dto = (DtoUsuario) ois.readObject();

				oos.writeObject(servico.loginOrRegisterFacebook(dto));
			}

			// BUSCAR
			if (request.getRequestURI().toLowerCase().endsWith("/buscar")) {
				FiltroUsuario filtro = new FiltroUsuario();
				if (request.getParameter("id") != null) {
					filtro.setId(Integer.decode(request.getParameter("id")));
				}
				if (request.getParameter("email") != null) {
					filtro.setEmail(request.getParameter("email"));
				}

				oos.writeObject(servico.buscarLista(filtro));
			}
		} catch (ExceptionFreelaSearch e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

}
