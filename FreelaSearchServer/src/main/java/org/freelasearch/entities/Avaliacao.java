package org.freelasearch.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.freelasearch.utils.BaseBean;

@Entity
@Table
public class Avaliacao extends BaseBean {

	private static final long serialVersionUID = 7134976199686069266L;

	@Id
	@GeneratedValue
	private Integer id;

	private Integer nota;

	private Date data;

	private String comentario;

	public Avaliacao() {
	}

	public Avaliacao(Integer id, Integer nota, Date data, String comentario) {
		super();
		this.id = id;
		this.nota = nota;
		this.data = data;
		this.comentario = comentario;
	}

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

	@Override
	public String toString() {
		return "Avaliacao [id=" + id + ", nota=" + nota + ", data=" + data + ", comentario=" + comentario + "]";
	}

}
