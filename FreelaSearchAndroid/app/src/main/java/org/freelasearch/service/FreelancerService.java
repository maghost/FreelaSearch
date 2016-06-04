package org.freelasearch.service;

import org.freelasearch.dtos.DtoFreelancer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FreelancerService extends AbstractService<DtoFreelancer> {

    public DtoFreelancer save(DtoFreelancer dto) throws IOException {
        return (DtoFreelancer) sendObject(dto, "freelancer/salvar");
    }

    public List<DtoFreelancer> findByFiltro(Map<String, String> params) throws IOException {
        return retrieveListObject(params, "freelancer/buscar");
    }

}
