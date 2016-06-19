package org.freelasearch.service;

import org.freelasearch.dtos.DtoMensagem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MensagemService extends AbstractService<DtoMensagem> {

    public DtoMensagem save(DtoMensagem dto) throws IOException {
        return (DtoMensagem) sendObject(dto, "mensagem/salvar");
    }

    public List<DtoMensagem> findByFiltro(Map<String, String> params) throws IOException {
        return retrieveListObject(params, "mensagem/buscar");
    }

}
