package org.freelasearch.services;

import java.util.ArrayList;
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

	public Anuncio montarBean(DtoAnuncio dto) {
		Anuncio anuncio = new Anuncio();
		anuncio.setId(dto.getId());
		anuncio.setTitulo(dto.getTitulo());
		anuncio.setDescricao(dto.getDescricao());

		return anuncio;
	}

	public DtoAnuncio montarDto(Integer id) {
		Anuncio anuncio = anuncioDao.findById(id);
		DtoAnuncio dto = new DtoAnuncio();
		dto.setId(anuncio.getId());
		dto.setTitulo(anuncio.getTitulo());
		dto.setDescricao(anuncio.getDescricao());

		return dto;
	}

	public List<DtoAnuncio> buscarLista(FiltroAnuncio filtro) {
		List<Anuncio> anuncios = anuncioDao.findByFiltro(filtro);
		List<DtoAnuncio> dtoAnuncios = new ArrayList<DtoAnuncio>();

		for (Anuncio anuncio : anuncios) {
			dtoAnuncios.add(AnuncioConverter.domainToDto(anuncio));
		}

		return dtoAnuncios;
	}
}
