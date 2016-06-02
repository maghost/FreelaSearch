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
@Table(name = "freelancer")
public class Freelancer extends BaseBean {

	private static final long serialVersionUID = 526603201080399158L;

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@OneToMany(mappedBy = "inscrito", fetch = FetchType.LAZY)
	private List<Inscricao> inscricoes;

	@OneToMany(mappedBy = "freelancer", fetch = FetchType.LAZY)
	private List<Contratacao> contratacoes;

	public Freelancer() {
	}

	public Freelancer(Integer id, Usuario usuario, List<Inscricao> inscricoes, List<Contratacao> contratacao) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.inscricoes = inscricoes;
		this.contratacoes = contratacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Inscricao> getInscricoes() {
		return inscricoes;
	}

	public void setInscricoes(List<Inscricao> inscricoes) {
		this.inscricoes = inscricoes;
	}

	public List<Contratacao> getContratacoes() {
		return contratacoes;
	}

	public void setContratacoes(List<Contratacao> contratacoes) {
		this.contratacoes = contratacoes;
	}

	@Override
	public String toString() {
		return "Freelancer [id=" + id + ", usuario=" + usuario + ", inscricoes=" + printSpecificFieldFromBeanCollection(inscricoes, "id") + ", contratacao="
				+ printSpecificFieldFromBeanCollection(contratacoes, "id") + "]";
	}

}
