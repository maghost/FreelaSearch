package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.service.AnuncianteService;
import org.freelasearch.service.InscricaoService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;

public class AsyncTaskInscricao extends AbstractAsyncTask<DtoInscricao, DtoInscricao> {

    @Override
    protected DtoInscricao executeService(DtoInscricao dtoInscricao) throws IOException {
        InscricaoService service = new InscricaoService();
        return service.save(dtoInscricao);
    }

}

