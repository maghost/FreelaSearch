package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoAnunciante;
import org.freelasearch.service.AnuncianteService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaAnunciante extends AbstractAsyncTask<Map<String, String>, List<DtoAnunciante>> {

    @Override
    protected List<DtoAnunciante> executeService(Map<String, String> params) throws IOException {
        AnuncianteService service = new AnuncianteService();
        return service.findByFiltro(params);
    }

}

