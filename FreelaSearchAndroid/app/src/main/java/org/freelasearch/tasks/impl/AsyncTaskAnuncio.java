package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.service.AnuncioService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;

public class AsyncTaskAnuncio extends AbstractAsyncTask<DtoAnuncio, DtoAnuncio> {

    @Override
    protected DtoAnuncio executeService(DtoAnuncio dtoAnuncio) throws IOException {
        AnuncioService service = new AnuncioService();
        return service.save(dtoAnuncio);
    }

}

