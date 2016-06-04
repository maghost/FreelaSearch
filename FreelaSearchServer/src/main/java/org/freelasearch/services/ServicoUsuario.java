package org.freelasearch.services;

import java.util.ArrayList;
import java.util.List;

import org.freelasearch.converters.UsuarioConverter;
import org.freelasearch.dao.UsuarioDao;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.entities.Usuario;
import org.freelasearch.filters.FiltroUsuario;
import org.freelasearch.utils.DaoFactory;
import org.freelasearch.utils.ExceptionFreelaSearch;

public class ServicoUsuario {

	private UsuarioDao usuarioDao;

	public ServicoUsuario() {
		usuarioDao = DaoFactory.usuarioInstance();
	}

	public void salvar(DtoUsuario dto) {
		Usuario usuario = UsuarioConverter.dtoToDomain(dto);

		if (usuario.getId() == null) {
			List<Usuario> listaPorEmail = usuarioDao.findByEmail(usuario.getEmail());

			// Se for login pelo facebook a senha virá nula
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

		dto = UsuarioConverter.domainToDto(usuario);
	}

	public DtoUsuario login(DtoUsuario dto) {
		List<Usuario> listUsuario = usuarioDao.findLogin(dto.getEmail(), dto.getSenha());
		if (listUsuario.size() != 1) {
			List<Usuario> listUsuarioPorEmail = usuarioDao.findByEmail(dto.getEmail());
			if (listUsuarioPorEmail.size() == 1) {
				if (listUsuarioPorEmail.get(0).getSenha() == null) {
					throw new ExceptionFreelaSearch(
							"O usuário informado não possui uma senha registrada. Logue-se pelo Facebook ou recupe a senha para ser enviada uma nova para o email registrado.");
				}
				throw new ExceptionFreelaSearch("A senha informada é inválida.");
			}
			throw new ExceptionFreelaSearch("Nenhuma conta foi encontrada com o e-mail informado.");
		}

		return UsuarioConverter.domainToDto(listUsuario.get(0));
	}

	public DtoUsuario loginOrRegisterFacebook(DtoUsuario dto) {
		Usuario usuario = UsuarioConverter.dtoToDomain(dto);

		List<Usuario> listaPorEmail = usuarioDao.findByEmail(usuario.getEmail());

		if (listaPorEmail.size() == 0) {
			usuarioDao.save(usuario);
			dto = UsuarioConverter.domainToDto(usuario);
		} else {
			Usuario usuarioAtualizado = listaPorEmail.get(0);

			if (usuarioAtualizado.getEmail().equals(usuario.getEmail()) && usuarioAtualizado.getNome().equals(usuario.getNome())
					&& usuarioAtualizado.getFoto().equals(usuario.getFoto())) {
				dto = UsuarioConverter.domainToDto(usuarioAtualizado);
			} else {
				usuarioAtualizado.setEmail(usuario.getEmail());
				usuarioAtualizado.setNome(usuario.getNome());
				usuarioAtualizado.setFoto(usuario.getFoto());

				usuarioDao.update(usuarioAtualizado);
				dto = UsuarioConverter.domainToDto(usuarioAtualizado);
			}
		}

		return dto;
	}

	public List<DtoUsuario> buscarLista(FiltroUsuario filtro) {
		List<Usuario> usuarios = usuarioDao.findByFiltro(filtro);
		List<DtoUsuario> listaDtoUsuario = new ArrayList<DtoUsuario>();

		for (Usuario usuario : usuarios) {
			listaDtoUsuario.add(UsuarioConverter.domainToDto(usuario));
		}

		return listaDtoUsuario;
	}

	public Usuario buscarDomain(FiltroUsuario filtro) {
		return usuarioDao.findByFiltro(filtro).get(0);
	}

}
