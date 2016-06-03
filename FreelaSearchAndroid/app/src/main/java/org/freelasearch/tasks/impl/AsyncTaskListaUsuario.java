package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.service.UsuarioService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AsyncTaskListaUsuario extends AbstractAsyncTask<Map<String, String>, List<DtoUsuario>> {

    @Override
    protected List<DtoUsuario> executeService(Map<String, String> params) throws IOException {
        UsuarioService service = new UsuarioService();
        return service.findByFiltro(params);
    }

}

