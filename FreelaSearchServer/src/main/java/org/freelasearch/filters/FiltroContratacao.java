package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroContratacao extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = -8986799023147479237L;

	private Integer idContratacao;
	private Integer idAnuncio;
	private Integer qtdRetorno;
	private Integer qtdExibida;
	private Integer idPrimeiroLista;
	private Integer idFreelancer;
	private Integer idAnunciante;

	public Integer getIdContratacao() {
		return idContratacao;
	}

	public void setIdContratacao(Integer idContratacao) {
		this.idContratacao = idContratacao;
	}

	public Integer getIdAnuncio() {
		return idAnuncio;
	}

	public void setIdAnuncio(Integer idAnuncio) {
		this.idAnuncio = idAnuncio;
	}

	public Integer getQtdRetorno() {
		return qtdRetorno;
	}

	public void setQtdRetorno(Integer qtdRetorno) {
		this.qtdRetorno = qtdRetorno;
	}

	public Integer getQtdExibida() {
		return qtdExibida;
	}

	public void setQtdExibida(Integer qtdExibida) {
		this.qtdExibida = qtdExibida;
	}

	public Integer getIdPrimeiroLista() {
		return idPrimeiroLista;
	}

	public void setIdPrimeiroLista(Integer idPrimeiroLista) {
		this.idPrimeiroLista = idPrimeiroLista;
	}

	public Integer getIdFreelancer() {
		return idFreelancer;
	}

	public void setIdFreelancer(Integer idFreelancer) {
		this.idFreelancer = idFreelancer;
	}

	public Integer getIdAnunciante() {
		return idAnunciante;
	}

	public void setIdAnunciante(Integer idAnunciante) {
		this.idAnunciante = idAnunciante;
	}

}
