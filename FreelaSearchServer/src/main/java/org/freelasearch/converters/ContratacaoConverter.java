package org.freelasearch.converters;

import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.entities.Contratacao;

public abstract class ContratacaoConverter {

	public static DtoContratacao domainToDto(Contratacao domain) {
		DtoContratacao dto = new DtoContratacao();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setData(domain.getData());
			dto.setAnuncio(AnuncioConverter.domainToDto(domain.getAnuncio()));
			dto.setFreelancer(FreelancerConverter.domainToDto(domain.getFreelancer()));
			dto.setAvaliacaoFreelancer(AvaliacaoConverter.domainToDto(domain.getAvaliacaoFreelancer()));
			dto.setAvaliacaoAnunciante(AvaliacaoConverter.domainToDto(domain.getAvaliacaoAnunciante()));
		}

		return dto;
	}

	public static Contratacao dtoToDomain(DtoContratacao dto) {
		Contratacao domain = new Contratacao();

		if (dto != null) {
			domain.setId(dto.getId());
			domain.setData(dto.getData());
			domain.setAnuncio(AnuncioConverter.dtoToDomain(dto.getAnuncio()));
			domain.setFreelancer(FreelancerConverter.dtoToDomain(dto.getFreelancer()));
			domain.setAvaliacaoFreelancer(AvaliacaoConverter.dtoToDomain(dto.getAvaliacaoFreelancer()));
			domain.setAvaliacaoAnunciante(AvaliacaoConverter.dtoToDomain(dto.getAvaliacaoAnunciante()));
		}

		return domain;
	}

}
