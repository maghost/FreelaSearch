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

	public DtoAnunciante salvar(DtoAnunciante dto) {
		Anunciante anunciante = AnuncianteConverter.dtoToDomain(dto);

		if (anunciante.getId() == null) {
			if ((anunciante.getUsuario().getId() == null || anunciante.getUsuario().getId().equals(0)) && anunciante.getUsuario().getEmail() != null) {
				ServicoUsuario servicoUsuario = new ServicoUsuario();
				FiltroUsuario filtroUsuario = new FiltroUsuario();
				filtroUsuario.setEmail(anunciante.getUsuario().getEmail());
				anunciante.setUsuario(servicoUsuario.buscarDomain(filtroUsuario));
			}
			anuncianteDao.save(anunciante);
		} else {
			anuncianteDao.update(anunciante);
		}

		return AnuncianteConverter.domainToDto(anunciante);
	}

	public List<DtoAnunciante> buscarLista(FiltroAnunciante filtro) {
		List<Anunciante> listaAnunciante = anuncianteDao.findByFiltro(filtro);
		List<DtoAnunciante> listaDtoAnunciante = new ArrayList<DtoAnunciante>();

		for (Anunciante anunciante : listaAnunciante) {
			listaDtoAnunciante.add(AnuncianteConverter.domainToDto(anunciante));
		}

		return listaDtoAnunciante;
	}

	public DtoAnunciante buscarPorId(Integer id) {
		return AnuncianteConverter.domainToDto(anuncianteDao.findById(id));
	}

}
