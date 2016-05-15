package org.freelasearch.utils;

import org.freelasearch.dtos.DtoCategoria;

import java.util.ArrayList;
import java.util.List;

public final class CategoriaUtils {

    public static int getPositionById(List<DtoCategoria> categorias, Integer id) {
        int posicao = 0;
        for (; posicao < categorias.size(); posicao++) {
            if (categorias.get(posicao).getId().equals(id)) {
                break;
            }
        }
        // Se passou por todos e não encontrou, então deve retornar para o primeiro
        return posicao == categorias.size() ? 0 : posicao;
    }

    public static List<String> getNamesByList(List<DtoCategoria> categorias) {
        List<String> nomes = new ArrayList<>();
        for (DtoCategoria categoria : categorias) {
            nomes.add(categoria.getNome());
        }
        return nomes;
    }

    public static DtoCategoria getByPosition(List<DtoCategoria> categorias, int position) {
        return categorias.get(position);
    }

}
