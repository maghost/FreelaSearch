package org.freelasearch.service;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoInscricao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InscricaoService extends AbstractService<DtoInscricao> {

    public DtoInscricao save(DtoInscricao dto) throws IOException {
        return (DtoInscricao) sendObject(dto, "inscricao/salvar");
    }

    public List<DtoInscricao> findByFiltro(Map<String, Integer> params) throws IOException {
        return retrieveListObject(params, "inscricao/buscar");
    }

}
