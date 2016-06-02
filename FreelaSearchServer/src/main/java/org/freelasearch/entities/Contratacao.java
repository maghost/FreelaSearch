package org.freelasearch.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.freelasearch.utils.BaseBean;

@Entity
@Table(name = "contratacao")
public class Contratacao extends BaseBean {

	private static final long serialVersionUID = -409675274366548532L;

	@Id
	@GeneratedValue
	private Integer id;

	private Date data;

	@OneToOne
	@JoinColumn(name = "anuncio_id")
	private Anuncio anuncio;

	@OneToOne
	@JoinColumn(name = "freelancer_id")
	private Freelancer freelancer;

	@OneToOne
	@JoinColumn(name = "avaliacao_freelancer_id")
	private Avaliacao avaliacaoFreelancer;

	@OneToOne
	@JoinColumn(name = "avaliacao_anunciante_id")
	private Avaliacao avaliacaoAnunciante;

	public Contratacao() {
	}

	public Contratacao(Integer id, Date data, Anuncio anuncio, Freelancer freelancer, Avaliacao avaliacaoFreelancer, Avaliacao avaliacaoAnunciante) {
		super();
		this.id = id;
		this.data = data;
		this.anuncio = anuncio;
		this.freelancer = freelancer;
		this.avaliacaoFreelancer = avaliacaoFreelancer;
		this.avaliacaoAnunciante = avaliacaoAnunciante;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public Freelancer getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(Freelancer freelancer) {
		this.freelancer = freelancer;
	}

	public Avaliacao getAvaliacaoFreelancer() {
		return avaliacaoFreelancer;
	}

	public void setAvaliacaoFreelancer(Avaliacao avaliacaoFreelancer) {
		this.avaliacaoFreelancer = avaliacaoFreelancer;
	}

	public Avaliacao getAvaliacaoAnunciante() {
		return avaliacaoAnunciante;
	}

	public void setAvaliacaoAnunciante(Avaliacao avaliacaoAnunciante) {
		this.avaliacaoAnunciante = avaliacaoAnunciante;
	}

	@Override
	public String toString() {
		return "Contratacao [id=" + id + ", data=" + data + ", anuncio=" + anuncio + ", freelancer=" + freelancer + ", avaliacaoFreelancer=" + avaliacaoFreelancer + ", avaliacaoAnunciante=" + avaliacaoAnunciante + "]";
	}

}
