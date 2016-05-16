package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.service.CategoriaService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaCategoria extends AbstractAsyncTask<Void, List<DtoCategoria>> {

    @Override
    protected List<DtoCategoria> executeService(Void params) throws IOException {
        CategoriaService service = new CategoriaService();
        return service.findAll();
    }

}

