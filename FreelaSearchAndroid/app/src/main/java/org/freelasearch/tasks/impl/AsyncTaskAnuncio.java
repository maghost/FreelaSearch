package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.service.UsuarioService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.Map;

public class AsyncTaskAnuncio extends AbstractAsyncTask<Map<String, String>, DtoUsuario> {

    @Override
    protected DtoUsuario executeService(Map<String, String> params) throws IOException {
        UsuarioService service = new UsuarioService();
        return service.findByParam(params);
    }

}

