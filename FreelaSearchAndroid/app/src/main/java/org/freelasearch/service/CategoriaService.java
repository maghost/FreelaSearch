package org.freelasearch.service;

import org.freelasearch.dtos.DtoCategoria;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CategoriaService extends AbstractService<DtoCategoria> {

    public DtoCategoria findById(Integer id) throws IOException {
        return retrieveObject(Collections.singletonMap("id", id), "categoria/buscar");
    }

    public List<DtoCategoria> findAll() throws IOException {
        return retrieveListObject(null, "categoria/buscar");
    }

}
