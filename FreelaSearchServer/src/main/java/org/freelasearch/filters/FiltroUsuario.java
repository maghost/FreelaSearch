package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroUsuario extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = -5835621627569244460L;

	private Integer id;
	private String email;
	private String senha;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
