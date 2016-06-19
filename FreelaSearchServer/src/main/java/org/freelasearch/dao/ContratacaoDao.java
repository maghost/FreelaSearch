package org.freelasearch.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Contratacao;
import org.freelasearch.filters.FiltroContratacao;
import org.freelasearch.utils.GenericDao;

public class ContratacaoDao extends GenericDao<Contratacao, Integer> {

	@SuppressWarnings("unchecked")
	public List<Contratacao> findByFiltro(FiltroContratacao filtro) {

		List<Contratacao> contratacaoes = new ArrayList<>();

		// Se filtro possuir o id da contratacao, faz a busca direta, se não, utiliza lógicas para filtrar
		if (filtro.getIdContratacao() != null) {
			Contratacao contratacao = findById(filtro.getIdContratacao());
			if (contratacao != null) {
				contratacaoes.add(contratacao);
			}
		} else {
			String query = "FROM Contratacao c WHERE 1=1 ";

			// Se estiver logado como freelancer, buscar todas as contratações onde o id do freelancer logado é o mesmo da contratação
			if (filtro.getIdFreelancer() != null) {
				query += " and c.freelancer.id = " + filtro.getIdFreelancer();
			}
			// Se estiver logado como anunciante, buscar todas as contratações onde o id do anunciante logado é o mesmo do anunciante do anúncio da contratação
			else if (filtro.getIdAnunciante() != null) {
				query += " and c.anuncio.anunciante.id = " + filtro.getIdAnunciante();
			}

			if (filtro.getIdPrimeiroLista() != 0) {
				query += " and c.id > " + filtro.getIdPrimeiroLista();
			}

			query += " ORDER BY c.id DESC";
			Query q = this.getEntityManager().createQuery(query);

			if (filtro.getQtdRetorno() != 0) {
				q.setFirstResult(filtro.getQtdExibida());
				q.setMaxResults(filtro.getQtdRetorno());
			}

			contratacaoes = (List<Contratacao>) q.getResultList();
		}
		return contratacaoes;
	}

}
