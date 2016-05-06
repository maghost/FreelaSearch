package org.freelasearch.dtos;

import java.io.Serializable;
import java.util.Date;

public class DtoAnuncio implements Serializable {

	private static final long serialVersionUID = -1832092211830965147L;

	public Integer id;
	public String imagem;
	public String titulo;
	public String descricao;
	public Date data;
	public DtoLocalizacao local;
	public DtoUsuario anunciante;
	public boolean ativo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public DtoLocalizacao getLocal() {
		return local;
	}

	public void setLocal(DtoLocalizacao local) {
		this.local = local;
	}

	public DtoUsuario getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(DtoUsuario anunciante) {
		this.anunciante = anunciante;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
