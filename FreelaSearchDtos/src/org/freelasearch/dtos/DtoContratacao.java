package org.freelasearch.dtos;

import java.io.Serializable;
import java.util.Date;

public class DtoContratacao implements Serializable {

	private static final long serialVersionUID = 9019842845348287807L;

	private Integer id;
	private Date data;
	private DtoAnuncio anuncio;
	private DtoFreelancer freelancer;
	private DtoAvaliacao avaliacaoFreelancer;
	private DtoAvaliacao avaliacaoAnunciante;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public DtoAnuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(DtoAnuncio anuncio) {
		this.anuncio = anuncio;
	}

	public DtoFreelancer getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(DtoFreelancer freelancer) {
		this.freelancer = freelancer;
	}

	public DtoAvaliacao getAvaliacaoFreelancer() {
		return avaliacaoFreelancer;
	}

	public void setAvaliacaoFreelancer(DtoAvaliacao avaliacaoFreelancer) {
		this.avaliacaoFreelancer = avaliacaoFreelancer;
	}

	public DtoAvaliacao getAvaliacaoAnunciante() {
		return avaliacaoAnunciante;
	}

	public void setAvaliacaoAnunciante(DtoAvaliacao avaliacaoAnunciante) {
		this.avaliacaoAnunciante = avaliacaoAnunciante;
	}

}
