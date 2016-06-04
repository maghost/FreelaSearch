package org.freelasearch.service;

import org.freelasearch.dtos.DtoUsuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioService extends AbstractService<DtoUsuario> {

    public DtoUsuario save(DtoUsuario dto) throws IOException {
        return (DtoUsuario) sendObject(dto, "usuario/salvar");
    }

    public DtoUsuario loginOrRegisterFacebook(DtoUsuario dto) throws IOException {
        return (DtoUsuario) sendObject(dto, "usuario/facebook");
    }

    public DtoUsuario login(DtoUsuario dto) throws IOException {
        return (DtoUsuario) sendObject(dto, "usuario/login");
    }

    public List<DtoUsuario> findByFiltro(Map<String, String> params) throws IOException {
        return retrieveListObject(params, "usuario/buscar");
    }

}
