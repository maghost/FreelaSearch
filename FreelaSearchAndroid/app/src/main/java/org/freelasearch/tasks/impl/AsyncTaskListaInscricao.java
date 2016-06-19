package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoInscricao;
import org.freelasearch.service.AnuncioService;
import org.freelasearch.service.InscricaoService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaInscricao extends AbstractAsyncTask<Map<String, Integer>, List<DtoInscricao>> {

    @Override
    protected List<DtoInscricao> executeService(Map<String, Integer> params) throws IOException {
        InscricaoService service = new InscricaoService();
        return service.findByFiltro(params);
    }

}

