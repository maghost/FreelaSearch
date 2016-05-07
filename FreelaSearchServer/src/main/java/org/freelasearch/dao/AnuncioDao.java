package org.freelasearch.dao;

import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Anuncio;
import org.freelasearch.utils.GenericDao;

public class AnuncioDao extends GenericDao<Anuncio, Integer> {

	@SuppressWarnings("unchecked")
	public List<Anuncio> findByFiltro(Integer qtdRetorno, Integer qtdExibida, Integer tipoBusca) {
		Query q = this.getEntityManager().createQuery("FROM Anuncio a  ORDER BY a.id");
		q.setFirstResult(qtdExibida);
		q.setMaxResults(qtdRetorno);

		List<Anuncio> anuncios = (List<Anuncio>) q.getResultList();
		return anuncios;
	}

}
