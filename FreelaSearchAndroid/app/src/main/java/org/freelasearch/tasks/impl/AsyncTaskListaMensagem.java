package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoMensagem;
import org.freelasearch.service.MensagemService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaMensagem extends AbstractAsyncTask<Map<String, String>, List<DtoMensagem>> {

    @Override
    protected List<DtoMensagem> executeService(Map<String, String> params) throws IOException {
        MensagemService service = new MensagemService();
        return service.findByFiltro(params);
    }

}

