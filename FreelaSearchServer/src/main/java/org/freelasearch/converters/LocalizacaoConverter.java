package org.freelasearch.converters;

import org.freelasearch.dtos.DtoLocalizacao;

public abstract class LocalizacaoConverter {

	public static DtoLocalizacao paramsToDto(String cidade, String estado) {
		DtoLocalizacao dto = new DtoLocalizacao();

		dto.setCidade(cidade);
		dto.setEstado(estado);
		dto.setPais("BR");

		return dto;
	}

}
