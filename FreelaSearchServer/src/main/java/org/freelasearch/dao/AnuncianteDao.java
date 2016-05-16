package org.freelasearch.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Anunciante;
import org.freelasearch.filters.FiltroAnunciante;
import org.freelasearch.utils.GenericDao;

public class AnuncianteDao extends GenericDao<Anunciante, Integer> {

	@SuppressWarnings("unchecked")
	public List<Anunciante> findByFiltro(FiltroAnunciante filtro) {
		List<Anunciante> anunciantes = new ArrayList<>();

		if (filtro.getIdAnunciante() != null) {
			anunciantes.add(findById(filtro.getIdAnunciante()));
		} else {
			String query = "FROM Anunciante t WHERE 1=1";

			if (filtro.getEmail() != null) {
				query += " and t.usuario.email = '" + filtro.getEmail() + "'";
			}

			Query q = this.getEntityManager().createQuery(query);
			anunciantes = (List<Anunciante>) q.getResultList();
		}
		return anunciantes;
	}

}
