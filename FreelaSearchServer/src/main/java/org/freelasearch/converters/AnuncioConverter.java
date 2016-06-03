package org.freelasearch.converters;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.entities.Anuncio;

public abstract class AnuncioConverter {

	public static DtoAnuncio domainToDto(Anuncio domain) {
		DtoAnuncio dto = new DtoAnuncio();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setTitulo(domain.getTitulo());
			dto.setDescricao(domain.getDescricao());
			dto.setData(domain.getData());
			dto.setLocalizacao(LocalizacaoConverter.paramsToDto(domain.getCidade(), domain.getEstado()));
			dto.setAnunciante(AnuncianteConverter.domainToDto(domain.getAnunciante()));

			if (domain.getCategoria() != null) {
				dto.setCategoria(CategoriaConverter.domainToDto(domain.getCategoria()));
			}

			dto.setStatus(domain.getStatus() != null ? domain.getStatus() : 1);
		}

		return dto;
	}

	public static Anuncio dtoToDomain(DtoAnuncio dto) {
		Anuncio domain = new Anuncio();

		if (dto != null) {
			domain.setId(dto.getId());
			domain.setTitulo(dto.getTitulo());
			domain.setDescricao(dto.getDescricao());
			domain.setData(dto.getData());
			domain.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
			domain.setCidade(dto.getLocalizacao().getCidade());
			domain.setEstado(dto.getLocalizacao().getEstado());
			if (dto.getCategoria() != null) {
				domain.setCategoria(CategoriaConverter.dtoToDomain(dto.getCategoria()));
			}
			domain.setAnunciante(AnuncianteConverter.dtoToDomain(dto.getAnunciante()));
		}

		return domain;
	}

}
