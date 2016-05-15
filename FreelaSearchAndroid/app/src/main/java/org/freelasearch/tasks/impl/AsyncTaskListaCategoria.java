package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.service.CategoriaService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaCategoria extends AbstractAsyncTask<Map<String, String>, List<DtoCategoria>> {

    @Override
    protected List<DtoCategoria> executeService(Map<String, String> params) throws IOException {
        CategoriaService service = new CategoriaService();
        return service.findAll();
    }

}

