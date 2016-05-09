package org.freelasearch.dao;

import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Anuncio;
import org.freelasearch.filters.FiltroAnuncio;
import org.freelasearch.utils.GenericDao;

public class AnuncioDao extends GenericDao<Anuncio, Integer> {

	@SuppressWarnings("unchecked")
	public List<Anuncio> findByFiltro(FiltroAnuncio filtro) {
		String query = "FROM Anuncio a ";
		if (filtro.getTipoBusca() != null && filtro.getTipoBusca() == 0) {
			query += " WHERE a.status = 0 ";
		} else if (filtro.getTipoBusca() != null && filtro.getTipoBusca() == 1 && filtro.getTipoBusca() != null) {
			query += " WHERE a.status = 0 ";
		} else if (filtro.getTipoBusca() != null && filtro.getTipoBusca() == 2 && filtro.getTipoBusca() != null) {
			query += " WHERE a.status = 0 ";
		}
		query += "ORDER BY a.id";
		Query q = this.getEntityManager().createQuery(query);
		q.setFirstResult(filtro.getQtdExibida());
		q.setMaxResults(filtro.getQtdRetorno());

		List<Anuncio> anuncios = (List<Anuncio>) q.getResultList();
		return anuncios;
	}

}
