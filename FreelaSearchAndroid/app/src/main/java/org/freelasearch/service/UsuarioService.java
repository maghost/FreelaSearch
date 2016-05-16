package org.freelasearch.service;

import org.freelasearch.dtos.DtoUsuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsuarioService extends AbstractService<DtoUsuario> {

    public void save(DtoUsuario dto) throws IOException {
        sendObject(dto, "usuario/salvar");
    }

    public DtoUsuario loginOrRegisterFacebook(DtoUsuario dto) throws IOException {
        Map<String, String> m = new HashMap<>();
        m.put("nome", dto.getNome());
        m.put("email", dto.getEmail());
        m.put("urlFoto", dto.getUrlFoto());

        return retrieveObject(m, "usuario/facebook");
    }

    public DtoUsuario login(DtoUsuario dto) throws IOException {
        Map<String, String> m = new HashMap<>();
        m.put("email", dto.getEmail());
        m.put("senha", dto.getSenha());
        return retrieveObject(m, "usuario/login");
    }

    public DtoUsuario findByParam(Map<String, String> params) throws IOException {
        return retrieveObject(params, "usuario/buscar");
    }

}
