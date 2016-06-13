package org.freelasearch.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Freelancer;
import org.freelasearch.filters.FiltroFreelancer;
import org.freelasearch.utils.GenericDao;

public class FreelancerDao extends GenericDao<Freelancer, Integer> {

	@SuppressWarnings("unchecked")
	public List<Freelancer> findByFiltro(FiltroFreelancer filtro) {
		List<Freelancer> freelancers = new ArrayList<>();

		if (filtro.getIdFreelancer() != null) {
			Freelancer freelancer = findById(filtro.getIdFreelancer());
			if (freelancer != null) {
				freelancers.add(freelancer);
			}
		} else {
			String query = "FROM Freelancer t WHERE 1=1";

			if (filtro.getIdUsuario() != null) {
				query += " and t.usuario.id = " + filtro.getIdUsuario();
			}
			if (filtro.getEmail() != null) {
				query += " and t.usuario.email = '" + filtro.getEmail() + "'";
			}

			Query q = this.getEntityManager().createQuery(query);
			freelancers = (List<Freelancer>) q.getResultList();
		}
		return freelancers;
	}

}
