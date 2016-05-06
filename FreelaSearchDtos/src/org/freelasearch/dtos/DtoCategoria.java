package org.freelasearch.dtos;

import java.io.Serializable;

public class DtoCategoria implements Serializable {

	private static final long serialVersionUID = -9028687280832460656L;

	public Integer id;
	public String nome;
	public String descricao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
