package org.freelasearch.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Mensagem;
import org.freelasearch.filters.FiltroMensagem;
import org.freelasearch.utils.GenericDao;

public class MensagemDao extends GenericDao<Mensagem, Integer> {

	@SuppressWarnings("unchecked")
	public List<Mensagem> findByFiltro(FiltroMensagem filtro) {

		List<Mensagem> mensagens = new ArrayList<>();

		// Se filtro possuir o id da mensagem, faz a busca direta, se não, utiliza lógicas para filtrar
		if (filtro.getIdMensagem() != null) {
			Mensagem mensagem = findById(filtro.getIdMensagem());
			if (mensagem != null) {
				mensagens.add(mensagem);
			}
		} else {
			String query = "FROM Mensagem m WHERE 1=1 ";

			if (filtro.getIdPrimeiroLista() != 0) {
				query += " and m.id > " + filtro.getIdPrimeiroLista();
			}

			query += " ORDER BY m.id DESC";
			Query q = this.getEntityManager().createQuery(query);

			if (filtro.getQtdRetorno() != 0) {
				q.setFirstResult(filtro.getQtdExibida());
				q.setMaxResults(filtro.getQtdRetorno());
			}

			mensagens = (List<Mensagem>) q.getResultList();
		}
		return mensagens;
	}

}
