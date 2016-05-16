package org.freelasearch.tasks.impl;

import org.freelasearch.dtos.DtoUsuario;
import org.freelasearch.service.UsuarioService;
import org.freelasearch.tasks.AbstractAsyncTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AsyncTaskFacebookUsuario extends AbstractAsyncTask<DtoUsuario, DtoUsuario> {

    @Override
    protected DtoUsuario executeService(DtoUsuario dto) throws IOException {
        UsuarioService service = new UsuarioService();
        return service.loginOrRegisterFacebook(dto);
    }

}

