package org.freelasearch.dao;

import java.util.List;

import org.freelasearch.entities.Categoria;
import org.freelasearch.utils.GenericDao;

public class CategoriaDao extends GenericDao<Categoria, Integer> {

	@SuppressWarnings("unchecked")
	public List<Categoria> findByCategoriaNome(String nome) {
		List<Categoria> categorias = (List<Categoria>) this.executeQuery("SELECT c FROM Categoria c WHERE c.nome = ?0", nome);
		return categorias;
	}

}
