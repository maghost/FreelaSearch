package org.freelasearch.converters;

import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.entities.Mensagem;

public abstract class MensagemConverter {

	public static DtoMensagem domainToDto(Mensagem domain) {
		DtoMensagem dto = new DtoMensagem();

		if (domain != null) {
			dto.setId(domain.getId());
			dto.setConteudo(domain.getConteudo());
			dto.setDataEnvio(domain.getDataEnvio());
			dto.setStatus(domain.getStatus());
			dto.setUsuarioRemetente(UsuarioConverter.domainToDto(domain.getUsuarioRemetente()));
			dto.setUsuarioDestinatario(UsuarioConverter.domainToDto(domain.getUsuarioDestinatario()));
		}

		return dto;
	}

	public static Mensagem dtoToDomain(DtoMensagem dto) {
		Mensagem domain = new Mensagem();

		if (dto != null) {
			domain.setId(dto.getId());
			domain.setConteudo(dto.getConteudo());
			domain.setDataEnvio(dto.getDataEnvio());
			domain.setStatus(dto.getStatus());
			domain.setUsuarioRemetente(UsuarioConverter.dtoToDomain(dto.getUsuarioRemetente()));
			domain.setUsuarioDestinatario(UsuarioConverter.dtoToDomain(dto.getUsuarioDestinatario()));
		}

		return domain;
	}

}
