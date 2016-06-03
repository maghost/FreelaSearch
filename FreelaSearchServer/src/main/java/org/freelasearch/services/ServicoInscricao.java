package org.freelasearch.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.freelasearch.converters.InscricaoConverter;
import org.freelasearch.dao.InscricaoDao;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.entities.Inscricao;
import org.freelasearch.filters.FiltroInscricao;
import org.freelasearch.utils.DaoFactory;

public class ServicoInscricao {

	private InscricaoDao inscricaoDao;

	public ServicoInscricao() {
		inscricaoDao = DaoFactory.inscricaoInstance();
	}

	public void salvar(DtoInscricao dto) {
		Inscricao inscricao = InscricaoConverter.dtoToDomain(dto);

		if (inscricao.getId() == null) {
			inscricao.setDataInscricao(new Date());
			inscricaoDao.save(inscricao);
		} else {
			inscricaoDao.update(inscricao);
		}

	}

	public List<DtoInscricao> buscarLista(FiltroInscricao filtro) {
		List<Inscricao> inscricoes = inscricaoDao.findByFiltro(filtro);
		List<DtoInscricao> listaDtoInscricao = new ArrayList<DtoInscricao>();

		for (Inscricao anuncio : inscricoes) {
			listaDtoInscricao.add(InscricaoConverter.domainToDto(anuncio));
		}

		return listaDtoInscricao;
	}

}
