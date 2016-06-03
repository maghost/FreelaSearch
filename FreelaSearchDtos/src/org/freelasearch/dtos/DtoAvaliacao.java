package org.freelasearch.dtos;

import java.io.Serializable;
import java.util.Date;

public class DtoAvaliacao implements Serializable {

	private static final long serialVersionUID = -8439021547045387081L;

	private Integer id;
	private Integer nota;
	private Date data;
	private String comentario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
