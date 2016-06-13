package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroFreelancer extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = -5992033679930745277L;

	private Integer idFreelancer;
	private Integer idUsuario;
	private String email;

	public Integer getIdFreelancer() {
		return idFreelancer;
	}

	public void setIdFreelancer(Integer idFreelancer) {
		this.idFreelancer = idFreelancer;
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
