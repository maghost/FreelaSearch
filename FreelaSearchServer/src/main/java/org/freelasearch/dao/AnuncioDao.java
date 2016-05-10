package org.freelasearch.dao;

import java.util.List;

import javax.persistence.Query;

import org.freelasearch.entities.Anuncio;
import org.freelasearch.filters.FiltroAnuncio;
import org.freelasearch.utils.GenericDao;

public class AnuncioDao extends GenericDao<Anuncio, Integer> {

	@SuppressWarnings("unchecked")
	public List<Anuncio> findByFiltro(FiltroAnuncio filtro) {

		// TODO: Verificar uma forma de deixar isso mais evidentes
		// Tipo Busca:
		// 0 = Todos os Anúncios Ativos
		// 1 = Meus Anúncios Ativos
		// 2 = Meus Anúncios Finalizados
		// 3 = Todos os Anúncios Finalizados
		// 4 = Todos os Anúncios (ativos e finalizados)

		String query = "FROM Anuncio a WHERE 1=1 ";
		if (filtro.getTipoBusca() != null) {
			if (filtro.getTipoBusca() == 0) {
				query += " and a.status = 0 ";
			} else if (filtro.getTipoBusca() == 1 && filtro.getIdUsuario() != null) {
				query += " and a.status = 0 and a.anunciante.usuario.id = " + filtro.getIdUsuario();
			} else if (filtro.getTipoBusca() == 2) {
				query += " and a.status = 1 and a.anunciante.usuario.id = " + filtro.getIdUsuario();
			} else if (filtro.getTipoBusca() == 3) {
				query += " and a.status = 1";
			}
		}

		if (filtro.getIdPrimeiroLista() != 0) {
			query += " and a.id > " + filtro.getIdPrimeiroLista();
		}

		query += " ORDER BY a.id DESC";
		Query q = this.getEntityManager().createQuery(query);

		if (filtro.getQtdRetorno() != 0) {
			q.setFirstResult(filtro.getQtdExibida());
			q.setMaxResults(filtro.getQtdRetorno());
		}

		List<Anuncio> anuncios = (List<Anuncio>) q.getResultList();
		return anuncios;
	}
}
