package org.freelasearch.dtos;

import java.io.Serializable;
import java.util.Date;

public class DtoInscricao implements Serializable {

	private static final long serialVersionUID = 9147122828373896217L;

	private Integer id;
	private DtoAnuncio anuncio;
	private DtoFreelancer freelancer;
	private Date data;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DtoAnuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(DtoAnuncio anuncio) {
		this.anuncio = anuncio;
	}

	public DtoFreelancer getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(DtoFreelancer freelancer) {
		this.freelancer = freelancer;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
