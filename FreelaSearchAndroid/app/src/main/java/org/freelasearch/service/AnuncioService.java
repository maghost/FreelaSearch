package org.freelasearch.service;

import org.freelasearch.dtos.DtoAnuncio;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AnuncioService extends AbstractService<DtoAnuncio> {

    public Integer save(DtoAnuncio dto) throws IOException {
        return (Integer) sendObject(dto, "anuncio/salvar");
    }

    public List<DtoAnuncio> findByFiltro(Map<String, Integer> params) throws IOException {
        return retrieveListObject(params, "anuncio/buscar");
    }

}
