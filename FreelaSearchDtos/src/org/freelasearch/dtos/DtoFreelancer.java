package org.freelasearch.dtos;

import java.io.Serializable;

public class DtoFreelancer implements Serializable {

	private static final long serialVersionUID = 5918752276706537983L;

	private Integer id;
	private DtoUsuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DtoUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(DtoUsuario usuario) {
		this.usuario = usuario;
	}

}
