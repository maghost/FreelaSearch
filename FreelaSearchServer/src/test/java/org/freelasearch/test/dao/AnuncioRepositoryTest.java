package org.freelasearch.test.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.freelasearch.dao.AnuncioDao;
import org.freelasearch.entities.Anuncio;
import org.freelasearch.utils.DaoFactory;
import org.junit.Test;

public class AnuncioRepositoryTest {

	private static final Logger LOGGER = Logger.getLogger(AnuncioRepositoryTest.class);

	private AnuncioDao anuncioDao = DaoFactory.anuncioInstance();

	@Test
	public void testFindAll() {
		List<Anuncio> anuncios = this.anuncioDao.findAll();
		LOGGER.info(anuncios);
	}

	@Test
	public void testFindById() {
		Anuncio anuncio = this.anuncioDao.findById(1);
		LOGGER.info(anuncio);
	}

}
