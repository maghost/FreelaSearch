package org.freelasearch.dtos;

import java.io.Serializable;

public class DtoViewFreelaSearch implements Serializable {

	private static final long serialVersionUID = -4520101821888051310L;

	private Integer id;

	private Integer totalAnuncio;
	private Integer totalAnunciante;
	private Integer totalFreelancer;
	private Integer totalInscricao;
	private Integer totalContratacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTotalAnuncio() {
		return totalAnuncio;
	}

	public void setTotalAnuncio(Integer totalAnuncio) {
		this.totalAnuncio = totalAnuncio;
	}

	public Integer getTotalAnunciante() {
		return totalAnunciante;
	}

	public void setTotalAnunciante(Integer totalAnunciante) {
		this.totalAnunciante = totalAnunciante;
	}

	public Integer getTotalFreelancer() {
		return totalFreelancer;
	}

	public void setTotalFreelancer(Integer totalFreelancer) {
		this.totalFreelancer = totalFreelancer;
	}

	public Integer getTotalInscricao() {
		return totalInscricao;
	}

	public void setTotalInscricao(Integer totalInscricao) {
		this.totalInscricao = totalInscricao;
	}

	public Integer getTotalContratacao() {
		return totalContratacao;
	}

	public void setTotalContratacao(Integer totalContratacao) {
		this.totalContratacao = totalContratacao;
	}

}
