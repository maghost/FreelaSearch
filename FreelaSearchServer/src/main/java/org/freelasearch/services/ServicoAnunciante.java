package org.freelasearch.services;

import java.util.ArrayList;
import java.util.List;

import org.freelasearch.converters.AnuncianteConverter;
import org.freelasearch.dao.AnuncianteDao;
import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.entities.Anunciante;
import org.freelasearch.filters.FiltroAnunciante;
import org.freelasearch.filters.FiltroUsuario;
import org.freelasearch.utils.DaoFactory;

public class ServicoAnunciante {

	private AnuncianteDao anuncianteDao;

	public ServicoAnunciante() {
		anuncianteDao = DaoFactory.anuncianteInstance();
	}

	public void salvar(DtoAnunciante dto) {
		Anunciante anunciante = AnuncianteConverter.dtoToDomain(dto);

		if (anunciante.getId() == null) {
			if ((anunciante.getUsuario().getId() == null || anunciante.getUsuario().getId().equals(0)) && anunciante.getUsuario().getEmail() != null) {
				ServicoUsuario servicoUsuario = new ServicoUsuario();
				FiltroUsuario filtroUsuario = new FiltroUsuario();
				filtroUsuario.setEmail(anunciante.getUsuario().getEmail());
				anunciante.setUsuario(servicoUsuario.buscarDomain(filtroUsuario));
			}
			anuncianteDao.save(anunciante);
			dto.setId(anunciante.getId());
		} else {
			anuncianteDao.update(anunciante);
		}

	}

	public List<DtoAnunciante> buscarLista(FiltroAnunciante filtro) {
		List<Anunciante> anunciantes = anuncianteDao.findByFiltro(filtro);
		List<DtoAnunciante> listaDtoAnunciante = new ArrayList<DtoAnunciante>();

		for (Anunciante anunciante : anunciantes) {
			listaDtoAnunciante.add(AnuncianteConverter.domainToDto(anunciante));
		}

		return listaDtoAnunciante;
	}

}
