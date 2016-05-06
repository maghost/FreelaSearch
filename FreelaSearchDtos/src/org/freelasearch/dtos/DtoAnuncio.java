package org.freelasearch.dtos;

import java.io.Serializable;
import java.util.Date;

public class DtoAnuncio implements Serializable {

	private static final long serialVersionUID = -1832092211830965147L;

	private Integer id;
	private String imagem;
	private String titulo;
	private String descricao;
	private Date data;
	private DtoLocalizacao local;
	private DtoUsuario anunciante;
	private DtoCategoria categoria;
	private boolean ativo;

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

	public DtoCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(DtoCategoria categoria) {
		this.categoria = categoria;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
