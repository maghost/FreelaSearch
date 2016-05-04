package org.freelasearch.dtos;

import java.io.Serializable;

public class DtoAnuncio implements Serializable {

	private static final long serialVersionUID = -1832092211830965147L;

	public Integer id;
	public String imagem;
	public String titulo;
	public String descricao;
	public DtoLocalizacao local;
	public boolean ativo;

}
