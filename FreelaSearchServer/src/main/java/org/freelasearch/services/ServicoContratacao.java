package org.freelasearch.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.freelasearch.converters.ContratacaoConverter;
import org.freelasearch.dao.AnuncioDao;
import org.freelasearch.dao.ContratacaoDao;
import org.freelasearch.dao.InscricaoDao;
import org.freelasearch.dao.MensagemDao;
import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.entities.Anuncio;
import org.freelasearch.entities.Contratacao;
import org.freelasearch.entities.Inscricao;
import org.freelasearch.entities.Mensagem;
import org.freelasearch.filters.FiltroContratacao;
import org.freelasearch.filters.FiltroInscricao;
import org.freelasearch.utils.DaoFactory;

public class ServicoContratacao {

	private ContratacaoDao contratacaoDao;
	private AnuncioDao anuncioDao;
	private InscricaoDao inscricaoDao;
	private MensagemDao mensagemDao;

	public ServicoContratacao() {
		contratacaoDao = DaoFactory.contratacaoInstance();
		anuncioDao = DaoFactory.anuncioInstance();
		inscricaoDao = DaoFactory.inscricaoInstance();
		mensagemDao = DaoFactory.mensagemInstance();
	}

	@Transactional
	public DtoContratacao salvar(DtoContratacao dto) {
		Contratacao contratacao = ContratacaoConverter.dtoToDomain(dto);

		if (contratacao.getId() == null) {
			contratacao.setData(new Date());
			contratacaoDao.save(contratacao);

			// Atualiza o status do Anúncio para Finalizado
			Anuncio anuncio = contratacao.getAnuncio();
			anuncio.setStatus(2);
			anuncioDao.update(anuncio);

			// TODO: Enviar notificação para freelancers não aceitos

			// TODO: Enviar notificação para freelancer aceito
			// TODO: Verificar possibilidade de chamar o serviço ao invés de fazer a lógica no serviço de contratação
			Mensagem mensagemFreelancer = new Mensagem();
			mensagemFreelancer.setConteudo("Parabéns! Sua inscrição para o Anúncio \"" + contratacao.getAnuncio().getTitulo()
					+ "\" foi aceita. Acesse \"Minhas Contratações\" para mais detalhes.");
			mensagemFreelancer.setUsuarioDestinatario(contratacao.getFreelancer().getUsuario());
			mensagemFreelancer.setDataEnvio(contratacao.getData());
			mensagemDao.save(mensagemFreelancer);

			// Apaga as inscrições do anúncio
			FiltroInscricao filtroInscricao = new FiltroInscricao();
			filtroInscricao.setIdAnuncio(contratacao.getAnuncio().getId());
			for (Inscricao inscricao : inscricaoDao.findByFiltro(filtroInscricao)) {
				inscricao.setStatusInscricao(3);
				inscricaoDao.update(inscricao);
			}
		} else {
			contratacaoDao.update(contratacao);
		}

		return ContratacaoConverter.domainToDto(contratacao);
	}

	public List<DtoContratacao> buscarLista(FiltroContratacao filtro) {
		List<Contratacao> contratacoes = contratacaoDao.findByFiltro(filtro);
		List<DtoContratacao> listaDtoContratacao = new ArrayList<DtoContratacao>();

		for (Contratacao contratacao : contratacoes) {
			listaDtoContratacao.add(ContratacaoConverter.domainToDto(contratacao));
		}

		return listaDtoContratacao;
	}

}
