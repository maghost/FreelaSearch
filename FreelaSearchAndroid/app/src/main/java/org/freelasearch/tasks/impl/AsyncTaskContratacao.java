package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoContratacao;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.service.ContratacaoService;
import org.freelasearch.service.InscricaoService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;

public class AsyncTaskContratacao extends AbstractAsyncTask<DtoContratacao, DtoContratacao> {

    @Override
    protected DtoContratacao executeService(DtoContratacao dtoContratacao) throws IOException {
        ContratacaoService service = new ContratacaoService();
        return service.save(dtoContratacao);
    }

}

