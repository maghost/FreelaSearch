package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroAnunciante extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = 4291821855158177138L;

	private Integer idAnunciante;
	private Integer idUsuario;
	private String email;

	public Integer getIdAnunciante() {
		return idAnunciante;
	}

	public void setIdAnunciante(Integer idAnunciante) {
		this.idAnunciante = idAnunciante;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
