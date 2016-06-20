package org.freelasearch.converters;

import org.freelasearch.dtos.DtoViewFreelaSearch;
import org.freelasearch.entities.ViewConsolidadoFreelaSearch;

public abstract class ViewFreelaSearchConverter {

	public static DtoViewFreelaSearch domainToDto(ViewConsolidadoFreelaSearch domain) {
		DtoViewFreelaSearch dto = new DtoViewFreelaSearch();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setTotalAnuncio(domain.getTotalAnuncio());
			dto.setTotalAnunciante(domain.getTotalAnunciante());
			dto.setTotalFreelancer(domain.getTotalFreelancer());
			dto.setTotalInscricao(domain.getTotalInscricao());
			dto.setTotalContratacao(domain.getTotalContratacao());
		}

		return dto;
	}

}
