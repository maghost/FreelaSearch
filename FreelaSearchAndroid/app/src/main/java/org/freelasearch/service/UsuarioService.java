package org.freelasearch.service;

import org.freelasearch.dtos.DtoUsuario;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by msms on 25/02/2016.
 */
public class UsuarioService extends AbstractService<DtoUsuario> {

    public void save(DtoUsuario dto) throws IOException {
        sendObject(dto, "usuario/salvar");
    }

    public DtoUsuario login(DtoUsuario dto) throws IOException {
        Map<String, String> m = new HashMap<String, String>();
        m.put("email", dto.getEmail());
        m.put("senha", dto.getSenha());
        return retrieveObject(m, "usuario/login");
    }

}
