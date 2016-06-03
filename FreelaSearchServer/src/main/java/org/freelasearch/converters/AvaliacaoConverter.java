package org.freelasearch.converters;

import org.freelasearch.dtos.DtoAvaliacao;
import org.freelasearch.entities.Avaliacao;

public abstract class AvaliacaoConverter {

	public static DtoAvaliacao domainToDto(Avaliacao domain) {
		DtoAvaliacao dto = new DtoAvaliacao();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setNota(domain.getNota());
			dto.setData(domain.getData());
			dto.setComentario(domain.getComentario());
		}

		return dto;
	}

	public static Avaliacao dtoToDomain(DtoAvaliacao dto) {
		Avaliacao domain = new Avaliacao();

		if (dto != null) {
			domain.setId(dto.getId());
			domain.setNota(dto.getNota());
			domain.setData(dto.getData());
			domain.setComentario(dto.getComentario());
		}

		return domain;
	}

}
