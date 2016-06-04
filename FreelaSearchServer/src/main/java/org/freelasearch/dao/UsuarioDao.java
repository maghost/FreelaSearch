package org.freelasearch.dao;

import java.util.ArrayList;
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
	public List<Usuario> findByFiltro(FiltroUsuario filtro) {
		List<Usuario> usuarios = new ArrayList<>();

		// Se filtro possuir o id do usuário, faz a busca direta, se não, utiliza lógicas para filtrar
		if (filtro.getId() != null) {
			usuarios.add(findById(filtro.getId()));
		} else {

			String query = "FROM Usuario u WHERE 1=1 ";
			if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
				query += " and u.email = '" + filtro.getEmail() + "'";
			}

			Query q = this.getEntityManager().createQuery(query);
			usuarios = (List<Usuario>) q.getResultList();
		}

		return usuarios;
	}
}
