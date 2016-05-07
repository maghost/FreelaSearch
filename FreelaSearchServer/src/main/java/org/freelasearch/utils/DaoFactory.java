package org.freelasearch.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.freelasearch.dao.AnuncianteDao;
import org.freelasearch.dao.AnuncioDao;
import org.freelasearch.dao.AvaliacaoDao;
import org.freelasearch.dao.CategoriaDao;
import org.freelasearch.dao.ChatDao;
import org.freelasearch.dao.ContratacaoDao;
import org.freelasearch.dao.FreelancerDao;
import org.freelasearch.dao.InscricaoDao;
import org.freelasearch.dao.MensagemDao;
import org.freelasearch.dao.UsuarioDao;

public final class DaoFactory {

	private DaoFactory() {
	}

	// /////////////////////////////////////////////////////////////////
	// ENTITY MANAGER FACTORY
	// /////////////////////////////////////////////////////////////////

	private static final String PERSISTENCE_UNIT_NAME = "freelasearchPersistenceUnit";

	private static EntityManagerFactory entityManagerFactoryInstance;

	public static EntityManagerFactory entityManagerFactorInstance() {
		if (entityManagerFactoryInstance == null) {
			entityManagerFactoryInstance = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}

		return entityManagerFactoryInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// ANUNCIANTE
	// /////////////////////////////////////////////////////////////////

	private static AnuncianteDao anuncianteDaoInstance;

	public static AnuncianteDao anuncianteInstance() {
		if (anuncianteDaoInstance == null) {
			anuncianteDaoInstance = new AnuncianteDao();
		}

		return anuncianteDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// ANUNCIO
	// /////////////////////////////////////////////////////////////////

	private static AnuncioDao anuncioDaoInstance;

	public static AnuncioDao anuncioInstance() {
		if (anuncioDaoInstance == null) {
			anuncioDaoInstance = new AnuncioDao();
		}

		return anuncioDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// AVALIACAO
	// /////////////////////////////////////////////////////////////////

	private static AvaliacaoDao avaliacaoDaoInstance;

	public static AvaliacaoDao avaliacaoInstance() {
		if (avaliacaoDaoInstance == null) {
			avaliacaoDaoInstance = new AvaliacaoDao();
		}

		return avaliacaoDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// CATEGORIA
	// /////////////////////////////////////////////////////////////////

	private static CategoriaDao categoriaDaoInstance;

	public static CategoriaDao categoriaInstance() {
		if (categoriaDaoInstance == null) {
			categoriaDaoInstance = new CategoriaDao();
		}

		return categoriaDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// CHAT
	// /////////////////////////////////////////////////////////////////

	private static ChatDao chatDaoInstance;

	public static ChatDao chatInstance() {
		if (chatDaoInstance == null) {
			chatDaoInstance = new ChatDao();
		}

		return chatDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// CONTRACACAO
	// /////////////////////////////////////////////////////////////////

	private static ContratacaoDao contratacaoDaoInstance;

	public static ContratacaoDao contratacaoInstance() {
		if (contratacaoDaoInstance == null) {
			contratacaoDaoInstance = new ContratacaoDao();
		}

		return contratacaoDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// FREELANCER
	// /////////////////////////////////////////////////////////////////

	private static FreelancerDao freelancerDaoInstance;

	public static FreelancerDao freelancerInstance() {
		if (freelancerDaoInstance == null) {
			freelancerDaoInstance = new FreelancerDao();
		}

		return freelancerDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// INSCRICAO
	// /////////////////////////////////////////////////////////////////

	private static InscricaoDao inscricaoDaoInstance;

	public static InscricaoDao inscricaoInstance() {
		if (inscricaoDaoInstance == null) {
			inscricaoDaoInstance = new InscricaoDao();
		}

		return inscricaoDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// MENSAGEM
	// /////////////////////////////////////////////////////////////////

	private static MensagemDao mensagemDaoInstance;

	public static MensagemDao mensagemInstance() {
		if (mensagemDaoInstance == null) {
			mensagemDaoInstance = new MensagemDao();
		}

		return mensagemDaoInstance;
	}

	// /////////////////////////////////////////////////////////////////
	// USUARIO
	// /////////////////////////////////////////////////////////////////

	private static UsuarioDao usuarioDaoInstance;

	public static UsuarioDao usuarioInstance() {
		if (usuarioDaoInstance == null) {
			usuarioDaoInstance = new UsuarioDao();
		}

		return usuarioDaoInstance;
	}

}
