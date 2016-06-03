package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroInscricao extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = -8795426923535141935L;

	private Integer idInscricao;
	private Integer idAnuncio;
	private Integer tipoBusca;
	private Integer qtdRetorno;
	private Integer qtdExibida;
	private Integer idPrimeiroLista;
	private Integer idUsuario;
	private Integer idFreelancer;

	public Integer getIdInscricao() {
		return idInscricao;
	}

	public void setIdInscricao(Integer idInscricao) {
		this.idInscricao = idInscricao;
	}

	public Integer getIdAnuncio() {
		return idAnuncio;
	}

	public void setIdAnuncio(Integer idAnuncio) {
		this.idAnuncio = idAnuncio;
	}

	public Integer getTipoBusca() {
		return tipoBusca;
	}

	public void setTipoBusca(Integer tipoBusca) {
		this.tipoBusca = tipoBusca;
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

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdFreelancer() {
		return idFreelancer;
	}

	public void setIdFreelancer(Integer idFreelancer) {
		this.idFreelancer = idFreelancer;
	}

}
