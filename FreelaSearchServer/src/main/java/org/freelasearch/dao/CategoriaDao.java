package org.freelasearch.dao;

import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Categoria;
import org.freelasearch.filters.FiltroCategoria;
import org.freelasearch.utils.GenericDao;

public class CategoriaDao extends GenericDao<Categoria, Integer> {

	@SuppressWarnings("unchecked")
	public List<Categoria> findByFiltro(FiltroCategoria filtro) {
		String query = "FROM " + Categoria.class.getName() + " t WHERE 1=1 ";
		query += " ORDER BY t.id DESC";
		Query q = this.getEntityManager().createQuery(query);

		if (filtro.getId() != null) {
			query += " t.id = :id ";
			q.setParameter("id", filtro.getId());
		}

		List<Categoria> categorias = (List<Categoria>) q.getResultList();
		return categorias;
	}

}
