package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.service.AnuncioService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaInscricao extends AbstractAsyncTask<Map<String, Integer>, List<DtoAnuncio>> {

    @Override
    protected List<DtoAnuncio> executeService(Map<String, Integer> params) throws IOException {
        AnuncioService service = new AnuncioService();
        return service.findByFiltro(params);
    }

}

