package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroAnuncio extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = -8129733193820433076L;

	private Integer tipoBusca;
	private Integer qtdRetorno;
	private Integer qtdExibida;
	private Integer idUsuario;

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

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

}
