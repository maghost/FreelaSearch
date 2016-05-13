package org.freelasearch.converters;

import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.entities.Anunciante;

public abstract class AnuncianteConverter {

	public static DtoAnunciante domainToDto(Anunciante domain) {
		DtoAnunciante dto = new DtoAnunciante();

		dto.setId(domain.getId());
		dto.setUsuario(UsuarioConverter.domainToDto(domain.getUsuario()));

		return dto;
	}

	public static Anunciante dtoToDomain(DtoAnunciante dto) {
		Anunciante domain = new Anunciante();

		domain.setId(dto.getId());
		domain.setUsuario(UsuarioConverter.dtoToDomain(dto.getUsuario()));

		return domain;
	}

}
