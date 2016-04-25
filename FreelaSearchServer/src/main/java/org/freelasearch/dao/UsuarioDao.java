package org.freelasearch.dao;

import java.util.List;

import org.freelasearch.entities.Usuario;
import org.freelasearch.utils.GenericDao;

public class UsuarioDao extends GenericDao<Usuario, Integer> {

	@SuppressWarnings("unchecked")
	public List<Usuario> findByEmail(String email) {
		List<Usuario> usuarios = (List<Usuario>) this.executeQuery("SELECT u FROM Usuario u WHERE u.email = ?0", email);
		return usuarios;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findLogin(String email, String senha) {
		// TODO Hash na senha
		List<Usuario> usuarios = (List<Usuario>) this.executeQuery("SELECT u FROM Usuario u WHERE u.email = ?0 AND u.senha = ?1", email, senha);

		return usuarios;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findLogin(String email) {
		List<Usuario> usuarios = (List<Usuario>) this.executeQuery("SELECT u FROM Usuario u WHERE u.email = ?0", email);

		return usuarios;
	}

}
