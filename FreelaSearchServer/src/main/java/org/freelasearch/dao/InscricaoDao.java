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
			inscricoes.add(findById(filtro.getIdInscricao()));
		} else {
			// TODO: Verificar uma forma de deixar isso mais evidente
			// Tipo Busca:
			// 1 = Minhas Inscrições
			// TODO: Depois se der tempo fazer as contratações separadas das inscrições, tanto no Server quanto no Android
			// 2 = Minhas Contratações (inscrições ativas)

			String query = "FROM Inscricao i WHERE 1=1 ";
			if (filtro.getTipoBusca() != null) {
				if (filtro.getTipoBusca() == 1 && filtro.getIdUsuario() != null) {
					query += " and i.inscrito.usuario.id = " + filtro.getIdUsuario();
					query += " and i.statusInscricao <> " + 1;
				} else if (filtro.getTipoBusca() == 2 && filtro.getIdUsuario() != null) {
					query += " and i.inscrito.usuario.id = " + filtro.getIdUsuario();
					query += " and i.statusInscricao = " + 1; // Inscrição aceita
				}
			}

			if (filtro.getIdPrimeiroLista() != 0) {
				query += " and i.id > " + filtro.getIdPrimeiroLista();
			}

			query += " ORDER BY i.id DESC";
			Query q = this.getEntityManager().createQuery(query);

			if (filtro.getQtdRetorno() != 0) {
				q.setFirstResult(filtro.getQtdExibida());
				q.setMaxResults(filtro.getQtdRetorno());
			}

			inscricoes = (List<Inscricao>) q.getResultList();
		}
		return inscricoes;
	}

}
