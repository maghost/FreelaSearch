package org.freelasearch.services;

import java.util.ArrayList;
import java.util.List;

import org.freelasearch.converters.ViewFreelaSearchConverter;
import org.freelasearch.dao.ViewFreelaSearchDao;
import org.freelasearch.dtos.DtoViewFreelaSearch;
import org.freelasearch.entities.ViewConsolidadoFreelaSearch;
import org.freelasearch.utils.DaoFactory;

public class ServicoViewFreelaSearch {

	private ViewFreelaSearchDao viewFreelaSearchDao;

	public ServicoViewFreelaSearch() {
		viewFreelaSearchDao = DaoFactory.viewFreelaSearchInstance();
	}

	public List<DtoViewFreelaSearch> buscar() {
		List<ViewConsolidadoFreelaSearch> consolidados = viewFreelaSearchDao.findAll();
		List<DtoViewFreelaSearch> listaDtoViewFreelaSearch = new ArrayList<DtoViewFreelaSearch>();

		for (ViewConsolidadoFreelaSearch domain : consolidados) {
			listaDtoViewFreelaSearch.add(ViewFreelaSearchConverter.domainToDto(domain));
		}

		return listaDtoViewFreelaSearch;
	}

}
