package org.freelasearch.dao;

import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Usuario;
import org.freelasearch.filters.FiltroUsuario;
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

	public Usuario findByFiltro(FiltroUsuario filtro) {
		String query = "FROM Usuario u WHERE 1=1 ";
		if (filtro.getId() != null) {
			query += " and u.id = " + filtro.getId();
		} else if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
			query += " and u.email = '" + filtro.getEmail() + "'";
		}
		Query q = this.getEntityManager().createQuery(query);

		Usuario usuario = (Usuario) q.getSingleResult();
		return usuario;
	}
}
