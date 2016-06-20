package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroMensagem extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = 1999401886825832089L;

	private Integer idMensagem;
	private Integer qtdRetorno;
	private Integer qtdExibida;
	private Integer idPrimeiroLista;
	private String conteudo;
	private String data;
	private Integer idUsuario;
	private Integer idContraparte;

	public Integer getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(Integer idMensagem) {
		this.idMensagem = idMensagem;
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

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdContraparte() {
		return idContraparte;
	}

	public void setIdContraparte(Integer idContraparte) {
		this.idContraparte = idContraparte;
	}

}
