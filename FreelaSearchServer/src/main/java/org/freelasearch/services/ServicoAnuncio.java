package org.freelasearch.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.freelasearch.converters.AnuncioConverter;
import org.freelasearch.dao.AnuncioDao;
import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.entities.Anuncio;
import org.freelasearch.filters.FiltroAnuncio;
import org.freelasearch.utils.DaoFactory;

public class ServicoAnuncio {

	private AnuncioDao anuncioDao;

	public ServicoAnuncio() {
		anuncioDao = DaoFactory.anuncioInstance();
	}

	public void salvar(DtoAnuncio dto) {
		Anuncio anuncio = AnuncioConverter.dtoToDomain(dto);

		if (anuncio.getId() == null) {
			anuncio.setData(new Date());
			anuncioDao.save(anuncio);
			dto.setId(anuncio.getId());
		} else {
			anuncioDao.update(anuncio);
		}
	}

	public List<DtoAnuncio> buscarLista(FiltroAnuncio filtro) {
		List<Anuncio> anuncios = anuncioDao.findByFiltro(filtro);
		List<DtoAnuncio> listaDtoAnuncio = new ArrayList<DtoAnuncio>();

		for (Anuncio anuncio : anuncios) {
			listaDtoAnuncio.add(AnuncioConverter.domainToDto(anuncio));
		}

		return listaDtoAnuncio;
	}

}
