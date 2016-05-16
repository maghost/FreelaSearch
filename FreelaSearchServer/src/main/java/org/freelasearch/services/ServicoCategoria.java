package org.freelasearch.services;

import java.util.ArrayList;
import java.util.List;

import org.freelasearch.converters.CategoriaConverter;
import org.freelasearch.dao.CategoriaDao;
import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.entities.Categoria;
import org.freelasearch.filters.FiltroCategoria;
import org.freelasearch.utils.DaoFactory;
import org.freelasearch.utils.ExceptionFreelaSearch;

public class ServicoCategoria {

	private CategoriaDao categoriaDao;

	public ServicoCategoria() {
		categoriaDao = DaoFactory.categoriaInstance();
	}

	public DtoCategoria buscarPorId(FiltroCategoria filtro) {
		Categoria categoria = categoriaDao.findById(filtro.getId());
		if (categoria == null) {
			throw new ExceptionFreelaSearch("Nenhuma categoria encontrada para o id #" + filtro.getId() + " informado.");
		}
		return CategoriaConverter.domainToDto(categoria);
	}

	public List<DtoCategoria> buscarLista() {
		List<Categoria> categorias = categoriaDao.findAll();
		List<DtoCategoria> listaDtoCategoria = new ArrayList<DtoCategoria>();

		for (Categoria categoria : categorias) {
			listaDtoCategoria.add(CategoriaConverter.domainToDto(categoria));
		}

		return listaDtoCategoria;
	}

}
