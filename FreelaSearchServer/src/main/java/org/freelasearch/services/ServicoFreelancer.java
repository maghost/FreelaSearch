package org.freelasearch.services;

import java.util.ArrayList;
import java.util.List;

import org.freelasearch.converters.FreelancerConverter;
import org.freelasearch.dao.FreelancerDao;
import org.freelasearch.dtos.DtoFreelancer;
import org.freelasearch.entities.Freelancer;
import org.freelasearch.filters.FiltroFreelancer;
import org.freelasearch.filters.FiltroUsuario;
import org.freelasearch.utils.DaoFactory;

public class ServicoFreelancer {

	private FreelancerDao freelancerDao;

	public ServicoFreelancer() {
		freelancerDao = DaoFactory.freelancerInstance();
	}

	public void salvar(DtoFreelancer dto) {
		Freelancer freelancer = FreelancerConverter.dtoToDomain(dto);

		if (freelancer.getId() == null) {
			if ((freelancer.getUsuario().getId() == null || freelancer.getUsuario().getId().equals(0)) && freelancer.getUsuario().getEmail() != null) {
				ServicoUsuario servicoUsuario = new ServicoUsuario();
				FiltroUsuario filtroUsuario = new FiltroUsuario();
				filtroUsuario.setEmail(freelancer.getUsuario().getEmail());
				freelancer.setUsuario(servicoUsuario.buscarDomain(filtroUsuario));
			}
			freelancerDao.save(freelancer);
			dto.setId(freelancer.getId());
		} else {
			freelancerDao.update(freelancer);
		}

	}

	public List<DtoFreelancer> buscarLista(FiltroFreelancer filtro) {
		List<Freelancer> freelancers = freelancerDao.findByFiltro(filtro);
		List<DtoFreelancer> listaDtoFreelancer = new ArrayList<DtoFreelancer>();

		for (Freelancer freelancer : freelancers) {
			listaDtoFreelancer.add(FreelancerConverter.domainToDto(freelancer));
		}

		return listaDtoFreelancer;
	}

}
