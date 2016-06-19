package org.freelasearch.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Inscricao;
import org.freelasearch.filters.FiltroInscricao;
import org.freelasearch.utils.GenericDao;

public class InscricaoDao extends GenericDao<Inscricao, Integer> {

	@SuppressWarnings("unchecked")
	public List<Inscricao> findByFiltro(FiltroInscricao filtro) {

		List<Inscricao> inscricoes = new ArrayList<>();

		// Se filtro possuir o id da inscrição, faz a busca direta, se não, utiliza lógicas para filtrar
		if (filtro.getIdInscricao() != null) {
			Inscricao inscricao = findById(filtro.getIdInscricao());
			if (inscricao != null) {
				inscricoes.add(inscricao);
			}
		} else {
			String query = "FROM Inscricao i WHERE 1=1 ";
			if (filtro.getTipoBusca() != null && filtro.getTipoBusca() == 1) {
				query += " and i.inscrito.usuario.id = " + filtro.getIdUsuario();
				query += " and i.statusInscricao <> " + 1;
			}

			if (filtro.getIdPrimeiroLista() != null && filtro.getIdPrimeiroLista() != 0) {
				query += " and i.id > " + filtro.getIdPrimeiroLista();
			}

			if (filtro.getIdAnuncio() != null) {
				query += " and i.anuncio.id = " + filtro.getIdAnuncio();
			}

			query += " ORDER BY i.id DESC";
			Query q = this.getEntityManager().createQuery(query);

			if (filtro.getQtdRetorno() != null && filtro.getQtdRetorno() != 0) {
				q.setFirstResult(filtro.getQtdExibida());
				q.setMaxResults(filtro.getQtdRetorno());
			}

			inscricoes = (List<Inscricao>) q.getResultList();
		}
		return inscricoes;
	}

}
