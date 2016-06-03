package org.freelasearch.service;

import org.freelasearch.dtos.DtoAnuncio;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InscricaoService extends AbstractService<DtoAnuncio> {

    public List<DtoAnuncio> findByFiltro(Map<String, Integer> params) throws IOException {
        return retrieveListObject(params, "inscricao/buscar");
    }

}
