package org.freelasearch.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.freelasearch.converters.UsuarioConverter;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.entities.Usuario;
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

				servico.salvar(dto);

				// Retorna o objeto preenchido para a aplicação
				oos.writeObject(dto);
			}

			// LOGIN
			if (request.getRequestURI().toLowerCase().endsWith("/login")) {
				String email = request.getParameter("email");
				String senha = request.getParameter("senha");
				oos.writeObject(servico.login(email, senha));
			}

			// TODO: Atualizar depois esse método para se comportar igual ao salvar
			// LOGIN/REGISTRO FACEBOOK
			if (request.getRequestURI().toLowerCase().endsWith("/facebook")) {
				DtoUsuario dto = new DtoUsuario();

				dto.setEmail(request.getParameter("email"));
				dto.setNome(request.getParameter("nome"));
				dto.setUrlFoto(request.getParameter("urlFoto"));

				Usuario usuario = UsuarioConverter.dtoToDomain(dto);
				oos.writeObject(UsuarioConverter.domainToDto(servico.loginOrRegisterFacebook(usuario)));
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
