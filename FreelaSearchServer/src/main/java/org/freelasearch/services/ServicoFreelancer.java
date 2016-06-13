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

	public DtoFreelancer salvar(DtoFreelancer dto) {
		Freelancer freelancer = FreelancerConverter.dtoToDomain(dto);

		if (freelancer.getId() == null) {
			if ((freelancer.getUsuario().getId() == null || freelancer.getUsuario().getId().equals(0)) && freelancer.getUsuario().getEmail() != null) {
				ServicoUsuario servicoUsuario = new ServicoUsuario();
				FiltroUsuario filtroUsuario = new FiltroUsuario();
				filtroUsuario.setEmail(freelancer.getUsuario().getEmail());
				freelancer.setUsuario(servicoUsuario.buscarDomain(filtroUsuario));
			}
			freelancerDao.save(freelancer);
		} else {
			freelancerDao.update(freelancer);
		}

		return FreelancerConverter.domainToDto(freelancer);
	}

	public List<DtoFreelancer> buscarLista(FiltroFreelancer filtro) {
		List<Freelancer> listaFreelancer = freelancerDao.findByFiltro(filtro);
		List<DtoFreelancer> listaDtoFreelancer = new ArrayList<DtoFreelancer>();

		for (Freelancer freelancer : listaFreelancer) {
			listaDtoFreelancer.add(FreelancerConverter.domainToDto(freelancer));
		}

		return listaDtoFreelancer;
	}

}
