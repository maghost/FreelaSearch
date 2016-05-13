package org.freelasearch.dtos;

import java.io.Serializable;

public class DtoAnunciante implements Serializable {

	private static final long serialVersionUID = 1665293001558683873L;
	
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
