package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.service.MensagemService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;

public class AsyncTaskMensagem extends AbstractAsyncTask<DtoMensagem, DtoMensagem> {

    @Override
    protected DtoMensagem executeService(DtoMensagem dtoMensagem) throws IOException {
        MensagemService service = new MensagemService();
        return service.save(dtoMensagem);
    }

}

