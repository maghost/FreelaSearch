package org.freelasearch.converters;

import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.entities.Freelancer;

public abstract class FreelancerConverter {

	public static DtoFreelancer domainToDto(Freelancer domain) {
		DtoFreelancer dto = new DtoFreelancer();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setUsuario(UsuarioConverter.domainToDto(domain.getUsuario()));
		}

		return dto;
	}

	public static Freelancer dtoToDomain(DtoFreelancer dto) {
		Freelancer domain = new Freelancer();

		if (dto != null) {
			domain.setId(dto.getId());
			domain.setUsuario(UsuarioConverter.dtoToDomain(dto.getUsuario()));
		}

		return domain;
	}

}
