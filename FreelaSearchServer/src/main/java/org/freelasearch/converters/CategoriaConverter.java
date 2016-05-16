package org.freelasearch.converters;

import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.entities.Categoria;

public abstract class CategoriaConverter {

	public static DtoCategoria domainToDto(Categoria domain) {
		DtoCategoria dto = new DtoCategoria();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setNome(domain.getNome());
			dto.setDescricao(domain.getDescricao());
		}

		return dto;
	}

	public static Categoria dtoToDomain(DtoCategoria dto) {
		Categoria domain = new Categoria();

		if (dto != null) {
			domain.setId(dto.getId());
			domain.setNome(dto.getNome());
			domain.setDescricao(dto.getDescricao());
		}

		return domain;
	}

}
