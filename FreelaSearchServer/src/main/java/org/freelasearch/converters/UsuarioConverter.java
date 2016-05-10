package org.freelasearch.converters;

import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.entities.Usuario;

public abstract class UsuarioConverter {

	public static DtoUsuario domainToDto(Usuario domain) {
		DtoUsuario dto = new DtoUsuario();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setEmail(domain.getEmail());
		dto.setSenha(domain.getSenha());
		dto.setUrlFoto(domain.getFoto());

		return dto;
	}

	public static Usuario dtoToDomain(DtoUsuario dto) {
		Usuario domain = new Usuario();

		domain.setId(dto.getId());
		domain.setNome(dto.getNome());
		domain.setEmail(dto.getEmail());
		domain.setSenha(dto.getSenha());
		domain.setFoto(dto.getUrlFoto());

		return domain;
	}

}
