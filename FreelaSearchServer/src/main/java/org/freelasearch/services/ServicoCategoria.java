package org.freelasearch.services;

import java.util.List;

import org.freelasearch.dao.CategoriaDao;
import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.entities.Categoria;
import org.freelasearch.utils.DaoFactory;
import org.freelasearch.utils.ExceptionFreelaSearch;

public class ServicoCategoria {

	private CategoriaDao categoriaDao;

	public ServicoCategoria() {
		categoriaDao = DaoFactory.categoriaInstance();
	}

	public Categoria montarBean(DtoCategoria dto) {
		Categoria categoria = new Categoria();
		categoria.setId(dto.getId());
		categoria.setNome(dto.getNome());
		categoria.setDescricao(dto.getDescricao());

		return categoria;
	}

	public DtoCategoria montarDto(Integer id) {
		Categoria categoria = categoriaDao.findById(id);
		DtoCategoria dto = new DtoCategoria();
		dto.setId(categoria.getId());
		dto.setNome(categoria.getNome());
		dto.setDescricao(categoria.getDescricao());

		return dto;
	}

	public void idValido(Integer id) {
		Categoria categoria = categoriaDao.findById(id);
		if (categoria == null) {
			throw new ExceptionFreelaSearch("Nenhuma categoria encontrada para o id #" + id + " informado.");
		}
	}

	public void salvar(Categoria categoria) {
		if (categoria.getId() == null) {
			List<Categoria> listaPorNome = categoriaDao.findByCategoriaNome(categoria.getNome());
			if (listaPorNome.size() > 0) {
				throw new ExceptionFreelaSearch("Categoria '" + categoria.getNome() + "' já cadastrada.");
			}
			categoriaDao.save(categoria);
		} else {
			categoriaDao.update(categoria);
		}
	}
}
