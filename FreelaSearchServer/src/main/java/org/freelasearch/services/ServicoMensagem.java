package org.freelasearch.services;

import java.util.ArrayList;
import java.util.List;

import org.freelasearch.converters.MensagemConverter;
import org.freelasearch.dao.MensagemDao;
import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.entities.Mensagem;
import org.freelasearch.filters.FiltroMensagem;
import org.freelasearch.utils.DaoFactory;

public class ServicoMensagem {

	private MensagemDao mensagemDao;

	public ServicoMensagem() {
		mensagemDao = DaoFactory.mensagemInstance();
	}

	public DtoMensagem salvar(DtoMensagem dto) {
		Mensagem mensagem = MensagemConverter.dtoToDomain(dto);

		if (mensagem.getId() == null) {
			mensagemDao.save(mensagem);
			// TODO : Verificar se deve ser adicionado em alguma tabela a relação da mensagem com quem recebeu/enviou
		} else {
			mensagemDao.update(mensagem);
		}

		return MensagemConverter.domainToDto(mensagem);
	}

	public List<DtoMensagem> buscarLista(FiltroMensagem filtro) {
		List<Mensagem> mensagens = mensagemDao.findByFiltro(filtro);
		List<DtoMensagem> listaDtoMensagem = new ArrayList<DtoMensagem>();

		for (Mensagem mensagem : mensagens) {
			listaDtoMensagem.add(MensagemConverter.domainToDto(mensagem));
		}

		return listaDtoMensagem;
	}

}
