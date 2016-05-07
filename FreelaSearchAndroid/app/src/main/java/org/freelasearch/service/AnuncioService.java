package org.freelasearch.service;

import org.freelasearch.dtos.DtoAnuncio;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AnuncioService extends AbstractService<DtoAnuncio> {

    public List<DtoAnuncio> findLimited(Map<String, Integer> params) throws IOException {
        return retrieveListObject(params, "anuncio/buscar");

        /*List<DtoAnuncio> anuncios = new ArrayList<>();

        for (int i = 1; i <= params.get("qtdRetorno"); i++) {
            DtoAnuncio anuncio = new DtoAnuncio();

            anuncio.setId(i);
            anuncio.setAtivo(true);
            anuncio.setTitulo("Meu Anúncio nº" + i);
            anuncio.setDescricao("Essa bosta está lenta");
            if (i % 2 == 0) {
                anuncio.setImagem("http://www.planwallpaper.com/static/images/b807c2282ab0a491bd5c5c1051c6d312_k4PiHxO.jpg");
            } else {
                anuncio.setImagem("http://eskipaper.com/images/wallpaper-hd-15.jpg");
            }

            anuncios.add(anuncio);
        }

        return anuncios;*/
    }

}
