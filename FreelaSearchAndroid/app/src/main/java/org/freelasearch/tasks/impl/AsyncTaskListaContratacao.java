package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.service.ContratacaoService;
import org.freelasearch.service.InscricaoService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaContratacao extends AbstractAsyncTask<Map<String, Integer>, List<DtoContratacao>> {

    @Override
    protected List<DtoContratacao> executeService(Map<String, Integer> params) throws IOException {
        ContratacaoService service = new ContratacaoService();
        return service.findByFiltro(params);
    }

}

