package converter;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.entities.Anuncio;

public abstract class AnuncioConverter {

	public static DtoAnuncio domainToDto(Anuncio domain) {
		DtoAnuncio dto = new DtoAnuncio();

		dto.setId(domain.getId());
		dto.setTitulo(domain.getTitulo());
		dto.setDescricao(domain.getDescricao());

		return dto;
	}

	public static Anuncio dtoToDomain(DtoAnuncio dto) {
		Anuncio domain = new Anuncio();

		domain.setId(dto.getId());
		domain.setTitulo(dto.getTitulo());
		domain.setDescricao(dto.getDescricao());

		return domain;
	}

}
