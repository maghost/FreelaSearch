package org.freelasearch.dtos;

import java.io.Serializable;

public class DtoLocalizacao implements Serializable {

	private static final long serialVersionUID = 4314845845830814914L;

	private String cidade;
	private String estado;
	private String pais;

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
