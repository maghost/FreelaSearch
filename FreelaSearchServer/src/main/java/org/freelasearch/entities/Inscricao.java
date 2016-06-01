package org.freelasearch.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.freelasearch.utils.BaseBean;

@Entity
@Table
public class Inscricao extends BaseBean {

	private static final long serialVersionUID = -3324044888631954198L;

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne
	@JoinColumn(name = "anuncio_id")
	private Anuncio anuncio;

	@OneToOne
	@JoinColumn(name = "freelancer_id")
	private Freelancer inscrito;

	@Column(name = "data_inscricao")
	private Date dataInscricao;

	@Column(name = "status_inscricao", columnDefinition = "TINYINT")
	private Integer statusInscricao;

	public Inscricao() {
	}

	public Inscricao(Integer id, Anuncio anuncio, Freelancer inscrito, Date dataInscricao, Integer inscricaoAceita) {
		super();
		this.id = id;
		this.anuncio = anuncio;
		this.inscrito = inscrito;
		this.dataInscricao = dataInscricao;
		this.statusInscricao = inscricaoAceita;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public Freelancer getInscrito() {
		return inscrito;
	}

	public void setInscrito(Freelancer inscrito) {
		this.inscrito = inscrito;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public Integer getInscricaoAceita() {
		return statusInscricao;
	}

	public void setStatusInscricao(Integer statusInscricao) {
		this.statusInscricao = statusInscricao;
	}

	@Override
	public String toString() {
		return "Inscricao [id=" + id + ", anuncio=" + anuncio + ", inscrito=" + inscrito + ", dataInscricao=" + dataInscricao + ", inscricaoAceita=" + statusInscricao + "]";
	}

}
