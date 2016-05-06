package org.freelasearch.services;

import java.util.List;

import org.freelasearch.dao.UsuarioDao;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.entities.Usuario;
import org.freelasearch.utils.DaoFactory;
import org.freelasearch.utils.ExceptionFreelaSearch;

public class ServicoUsuario {

	private UsuarioDao usuarioDao;

	public ServicoUsuario() {
		usuarioDao = DaoFactory.usuarioInstance();
	}

	public Usuario montarBean(DtoUsuario dto) {
		Usuario usuario = new Usuario();
		usuario.setId(dto.getId());
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());

		return usuario;
	}

	public DtoUsuario montarDto(Integer id) {
		Usuario usuario = usuarioDao.findById(id);
		DtoUsuario dto = new DtoUsuario();

		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail());
		dto.setSenha(usuario.getSenha());

		return dto;
	}

	public void salvar(Usuario usuario) {
		if (usuario.getId() == null) {
			List<Usuario> listaPorEmail = usuarioDao.findByEmail(usuario.getEmail());

			// Se for login pelo facebook a senha virá nula ou estará vazia
			if (usuario.getSenha() == null) {
				if (listaPorEmail.size() > 1) {
					throw new ExceptionFreelaSearch("Falha ao cadastrar via Facebook.");
				}
				if (listaPorEmail.size() == 0) {
					usuarioDao.save(usuario);
				} else {
					Usuario usuarioAtualizado = listaPorEmail.get(0);
					if (usuario.getNome() != usuarioAtualizado.getNome()) {
						usuarioAtualizado.setNome(usuario.getNome());
						usuarioDao.update(usuarioAtualizado);
					}
				}
			} else {
				if (listaPorEmail.size() > 0) {
					throw new ExceptionFreelaSearch("E-mail '" + usuario.getEmail() + "' já utilizado.");
				}
				usuarioDao.save(usuario);
			}
		} else {
			usuarioDao.update(usuario);
		}
	}

	public DtoUsuario login(String email, String senha) {
		List<Usuario> listUsuario = usuarioDao.findLogin(email, senha);
		if (listUsuario.size() != 1) {
			List<Usuario> listUsuarioSemSenha = usuarioDao.findLogin(email);
			if (listUsuarioSemSenha.size() == 1) {
				if (listUsuarioSemSenha.get(0).getSenha() == null) {
					throw new ExceptionFreelaSearch(
							"O usuário informado não possui uma senha registrada. Logue-se pelo Facebook ou recupe a senha para ser enviada uma nova para o email registrado.");
				}
				throw new ExceptionFreelaSearch("A senha informada é inválida.");

			}
			throw new ExceptionFreelaSearch("Nenhuma conta foi encontrada com o e-mail informado.");
		}

		Usuario usuario = listUsuario.get(0);

		DtoUsuario dto = new DtoUsuario();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail());
		dto.setSenha(usuario.getSenha());

		return dto;
	}

}
