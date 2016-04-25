package org.freelasearch.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.freelasearch.utils.BaseBean;

@Entity
@Table
public class Portfolio extends BaseBean {

	private static final long serialVersionUID = 8245308077053480525L;

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne
	@JoinColumn(name = "freelancer_id")
	private Freelancer freelancer;

	private String titulo;

	private String conteudo;

	@OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
	private List<Foto> fotos;

	public Portfolio() {
	}

	public Portfolio(Integer id, Freelancer freelancer, String titulo, String conteudo, List<Foto> fotos) {
		super();
		this.id = id;
		this.freelancer = freelancer;
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.fotos = fotos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Freelancer getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(Freelancer freelancer) {
		this.freelancer = freelancer;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	@Override
	public String toString() {
		return "Portfolio [id=" + id + ", freelancer=" + freelancer + ", titulo=" + titulo + ", conteudo=" + conteudo + ", fotos=" + printSpecificFieldFromBeanCollection(fotos, "nome") + "]";
	}

}
