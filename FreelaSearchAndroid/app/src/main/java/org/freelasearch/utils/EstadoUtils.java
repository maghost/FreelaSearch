package org.freelasearch.utils;

import java.util.ArrayList;
import java.util.List;

public class EstadoUtils {
    private String uf;
    private String descricao;

    public EstadoUtils() {
    }

    public EstadoUtils(String uf, String descricao) {
        this.uf = uf;
        this.descricao = descricao;
    }

    public String getUf() {
        return uf;
    }

    public String getDescricao() {
        return descricao;
    }

    public String toString() {
        return (this.getDescricao());
    }

    public ArrayList preencherEstados() {
        ArrayList estados = new ArrayList();
        estados.addAll(buscarEstados());
        return estados;
    }

    private List<EstadoUtils> buscarEstados() {
        List<EstadoUtils> estados = new ArrayList<>();
        estados.add(new EstadoUtils("AC", "Acre"));
        estados.add(new EstadoUtils("AL", "Alagoas"));
        estados.add(new EstadoUtils("AP", "Amapá"));
        estados.add(new EstadoUtils("AM", "Amazonas"));
        estados.add(new EstadoUtils("BA", "Bahia"));
        estados.add(new EstadoUtils("CE", "Ceará"));
        estados.add(new EstadoUtils("DF", "Distrito Federal"));
        estados.add(new EstadoUtils("ES", "Espírito Santo"));
        estados.add(new EstadoUtils("GO", "Goiás"));
        estados.add(new EstadoUtils("MA", "Maranhão"));
        estados.add(new EstadoUtils("MT", "Mato Grosso"));
        estados.add(new EstadoUtils("MS", "Mato Grosso do Sul"));
        estados.add(new EstadoUtils("MG", "Minas Gerais"));
        estados.add(new EstadoUtils("PA", "Pará"));
        estados.add(new EstadoUtils("PB", "Paraíba"));
        estados.add(new EstadoUtils("PR", "Paraná"));
        estados.add(new EstadoUtils("PE", "Pernambuco"));
        estados.add(new EstadoUtils("PI", "Piauí"));
        estados.add(new EstadoUtils("RJ", "Rio de Janeiro"));
        estados.add(new EstadoUtils("RN", "Rio Grande do Norte"));
        estados.add(new EstadoUtils("RS", "Rio Grande do Sul"));
        estados.add(new EstadoUtils("RO", "Rondônia"));
        estados.add(new EstadoUtils("RR", "Roraima"));
        estados.add(new EstadoUtils("SC", "Santa Catarina"));
        estados.add(new EstadoUtils("SP", "São Paulo"));
        estados.add(new EstadoUtils("SE", "Sergipe"));
        estados.add(new EstadoUtils("TO", "Tocantins"));
        return estados;
    }

    public String getDescriptionByUf(String uf) {
        String estado = "N/A";
        List<EstadoUtils> estados = buscarEstados();
        for (EstadoUtils e : estados) {
            if (e.getUf().equals(uf)) {
                estado = e.getDescricao();
            }
        }
        return estado;
    }

    public int getPositionByUf(String uf) {
        int posicao = 0;
        List<EstadoUtils> estados = buscarEstados();
        for (; posicao < estados.size(); posicao++) {
            if (estados.get(posicao).getUf().equals(uf)) {
                break;
            }
        }
        // Se passou por todos e não encontrou, então deve retornar para o primeiro
        return posicao == estados.size() ? 0 : posicao;
    }

}
