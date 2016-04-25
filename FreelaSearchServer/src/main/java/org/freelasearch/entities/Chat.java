package org.freelasearch.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.freelasearch.utils.BaseBean;

@Entity
@Table
public class Chat extends BaseBean {

	private static final long serialVersionUID = -2877571702340629170L;

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne
	@JoinColumn(name = "inscricao_id")
	private Inscricao inscricao;

	public Chat() {
	}

	public Chat(Integer id, Inscricao inscricao) {
		super();
		this.id = id;
		this.inscricao = inscricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Inscricao getInscricao() {
		return inscricao;
	}

	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", inscricao=" + inscricao + "]";
	}
	
	

}
