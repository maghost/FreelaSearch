package org.freelasearch.converters;

import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.entities.Inscricao;

public abstract class InscricaoConverter {

	public static DtoInscricao domainToDto(Inscricao domain) {
		DtoInscricao dto = new DtoInscricao();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setAnuncio(AnuncioConverter.domainToDto(domain.getAnuncio()));
			dto.setFreelancer(FreelancerConverter.domainToDto(domain.getInscrito()));
			dto.setData(domain.getDataInscricao());
			dto.setStatus(domain.getStatusInscricao());
		}

		return dto;
	}

	public static Inscricao dtoToDomain(DtoInscricao dto) {
		Inscricao domain = new Inscricao();

		if (dto != null) {
			domain.setId(dto.getId());
			domain.setAnuncio(AnuncioConverter.dtoToDomain(dto.getAnuncio()));
			domain.setInscrito(FreelancerConverter.dtoToDomain(dto.getFreelancer()));
			domain.setDataInscricao(dto.getData());
			domain.setStatusInscricao(dto.getStatus());
		}

		return domain;
	}

}
