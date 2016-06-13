package org.freelasearch.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.freelasearch.converters.ContratacaoConverter;
import org.freelasearch.dao.ContratacaoDao;
import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.entities.Contratacao;
import org.freelasearch.filters.FiltroContratacao;
import org.freelasearch.utils.DaoFactory;

public class ServicoContratacao {

	private ContratacaoDao contratacaoDao;

	public ServicoContratacao() {
		contratacaoDao = DaoFactory.contratacaoInstance();
	}

	public DtoContratacao salvar(DtoContratacao dto) {
		Contratacao contratacao = ContratacaoConverter.dtoToDomain(dto);

		if (contratacao.getId() == null) {
			contratacao.setData(new Date());
			contratacaoDao.save(contratacao);
		} else {
			contratacaoDao.update(contratacao);
		}

		return ContratacaoConverter.domainToDto(contratacao);
	}

	public List<DtoContratacao> buscarLista(FiltroContratacao filtro) {
		List<Contratacao> contratacoes = contratacaoDao.findByFiltro(filtro);
		List<DtoContratacao> listaDtoContratacao = new ArrayList<DtoContratacao>();

		for (Contratacao contratacao : contratacoes) {
			listaDtoContratacao.add(ContratacaoConverter.domainToDto(contratacao));
		}

		return listaDtoContratacao;
	}

}
