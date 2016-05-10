package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoAnuncio;
import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.service.AnuncioService;
import org.freelasearch.service.UsuarioService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskUsuario extends AbstractAsyncTask<Map<String, String>, DtoUsuario> {

    @Override
    protected DtoUsuario executeService(Map<String, String> params) throws IOException {
        UsuarioService service = new UsuarioService();
        return service.findByParam(params);
    }

}

