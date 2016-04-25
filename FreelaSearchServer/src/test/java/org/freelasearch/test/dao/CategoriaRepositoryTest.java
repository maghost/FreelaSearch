package org.freelasearch.test.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.freelasearch.dao.CategoriaDao;
import org.freelasearch.entities.Categoria;
import org.freelasearch.utils.DaoFactory;
import org.junit.Test;

public class CategoriaRepositoryTest {

	private static final Logger LOGGER = Logger.getLogger(CategoriaRepositoryTest.class);

	private CategoriaDao categoriaDao = DaoFactory.categoriaInstance();

	@Test
	public void testFindAll() {
		List<Categoria> categorias = this.categoriaDao.findAll();

		LOGGER.info(categorias);
	}

	@Test
	public void testFindById() {
		Categoria categoria = this.categoriaDao.findById(1);
		LOGGER.info(categoria);
	}

}
