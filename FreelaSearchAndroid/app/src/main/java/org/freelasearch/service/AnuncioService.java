package org.freelasearch.service;

import org.freelasearch.dtos.DtoAnuncio;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AnuncioService extends AbstractService<DtoAnuncio> {

    public void save(DtoAnuncio dto) throws IOException {
        sendObject(dto, "anuncio/salvar");
    }

    public List<DtoAnuncio> findLimited(Map<String, Integer> params) throws IOException {
        return retrieveListObject(params, "anuncio/buscar");
    }

}
