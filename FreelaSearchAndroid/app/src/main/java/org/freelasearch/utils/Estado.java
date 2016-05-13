package org.freelasearch.utils;

import java.util.ArrayList;

public class Estado {
    private String uf;
    private String descricao;

    public Estado() {
    }

    public Estado(String uf, String descricao) {
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

        estados.add(new Estado("AC", "Acre"));
        estados.add(new Estado("AL", "Alagoas"));
        estados.add(new Estado("AP", "Amapá"));
        estados.add(new Estado("AM", "Amazonas"));
        estados.add(new Estado("BA", "Bahia"));
        estados.add(new Estado("CE", "Ceará"));
        estados.add(new Estado("DF", "Distrito Federal"));
        estados.add(new Estado("ES", "Espírito Santo"));
        estados.add(new Estado("GO", "Goiás"));
        estados.add(new Estado("MA", "Maranhão"));
        estados.add(new Estado("MT", "Mato Grosso"));
        estados.add(new Estado("MS", "Mato Grosso do Sul"));
        estados.add(new Estado("MG", "Minas Gerais"));
        estados.add(new Estado("PA", "Pará"));
        estados.add(new Estado("PB", "Paraíba"));
        estados.add(new Estado("PR", "Paraná"));
        estados.add(new Estado("PE", "Pernambuco"));
        estados.add(new Estado("PI", "Piauí"));
        estados.add(new Estado("RJ", "Rio de Janeiro"));
        estados.add(new Estado("RN", "Rio Grande do Norte"));
        estados.add(new Estado("RS", "Rio Grande do Sul"));
        estados.add(new Estado("RO", "Rondônia"));
        estados.add(new Estado("RR", "Roraima"));
        estados.add(new Estado("SC", "Santa Catarina"));
        estados.add(new Estado("SP", "São Paulo"));
        estados.add(new Estado("SE", "Sergipe"));
        estados.add(new Estado("TO", "Tocantins"));

        return estados;
    }

    public String getByUf(String uf) {
        // TODO: Verificar uma maneira melhor para trabalhar com os estados
        String estado;
        switch (uf) {
            case "AC":
                estado = "Acre";
            case "AL":
                estado = "Alagoas";
            case "AP":
                estado = "Amapá";
            case "AM":
                estado = "Amazonas";
            case "BA":
                estado = "Bahia";
            case "CE":
                estado = "Ceará";
            case "DF":
                estado = "Distrito Federal";
            case "ES":
                estado = "Espírito Santo";
            case "GO":
                estado = "Goiás";
            case "MA":
                estado = "Maranhão";
            case "MT":
                estado = "Mato Grosso";
            case "MS":
                estado = "Mato Grosso do Sul";
            case "MG":
                estado = "Minas Gerais";
            case "PA":
                estado = "Pará";
            case "PB":
                estado = "Paraíba";
            case "PR":
                estado = "Paraná";
            case "PE":
                estado = "Pernambuco";
            case "PI":
                estado = "Piauí";
            case "RJ":
                estado = "Rio de Janeiro";
            case "RN":
                estado = "Rio Grande do Norte";
            case "RS":
                estado = "Rio Grande do Sul";
            case "RO":
                estado = "Rondônia";
            case "RR":
                estado = "Roraima";
            case "SC":
                estado = "Santa Catarina";
            case "SP":
                estado = "São Paulo";
            case "SE":
                estado = "Sergipe";
            case "TO":
                estado = "Tocantins";
            default:
                estado = "";
        }

        return estado;
    }

}
