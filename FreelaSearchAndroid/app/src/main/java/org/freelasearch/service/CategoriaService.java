package org.freelasearch.service;

import org.freelasearch.dtos.DtoCategoria;

import java.io.IOException;
import java.util.Collections;

public class CategoriaService extends AbstractService<DtoCategoria> {

    public void save(DtoCategoria dto) throws IOException {
        sendObject(dto, "categoria/salvar");
    }

    public void delete(DtoCategoria dto) throws IOException {
        sendObject(dto, "categoria/excluir");
    }

    public DtoCategoria findById(Integer id) throws IOException {
        return retrieveObject(Collections.singletonMap("id", id), "categoria/buscar");
    }
}
