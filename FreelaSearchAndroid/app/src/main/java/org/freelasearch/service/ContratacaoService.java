package org.freelasearch.service;

import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.dtos.DtoInscricao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ContratacaoService extends AbstractService<DtoContratacao> {

    public DtoContratacao save(DtoContratacao dto) throws IOException {
        return (DtoContratacao) sendObject(dto, "contratacao/salvar");
    }

    public List<DtoContratacao> findByFiltro(Map<String, Integer> params) throws IOException {
        return retrieveListObject(params, "contratacao/buscar");
    }

}
