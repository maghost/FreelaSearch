package org.freelasearch.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.freelasearch.utils.BaseBean;
import org.hibernate.annotations.Subselect;

@Entity
@Subselect("SELECT count(anuncio.id) totalAnuncio, " + "(SELECT count(anunciante.id) FROM anunciante) totalAnunciante,"
		+ "(SELECT count(freelancer.id) FROM freelancer) totalFreelancer," + "(SELECT count(inscricao.id) FROM inscricao) totalInscricao,"
		+ "(SELECT count(contratacao.id) FROM contratacao) totalContratacao" + "FROM anuncio")
public class ViewConsolidadoFreelaSearch extends BaseBean {

	private static final long serialVersionUID = 6605620496640076623L;

	@Id
	@GeneratedValue
	private Integer id;

	private Integer totalAnuncio;
	private Integer totalAnunciante;
	private Integer totalFreelancer;
	private Integer totalInscricao;
	private Integer totalContratacao;

	public ViewConsolidadoFreelaSearch() {
	}

	public ViewConsolidadoFreelaSearch(Integer id, Integer totalAnuncio, Integer totalAnunciante, Integer totalFreelancer, Integer totalInscricao,
			Integer totalContratacao) {
		super();
		this.id = id;
		this.totalAnuncio = totalAnuncio;
		this.totalAnunciante = totalAnunciante;
		this.totalFreelancer = totalFreelancer;
		this.totalInscricao = totalInscricao;
		this.totalContratacao = totalContratacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTotalAnuncio() {
		return totalAnuncio;
	}

	public void setTotalAnuncio(Integer totalAnuncio) {
		this.totalAnuncio = totalAnuncio;
	}

	public Integer getTotalAnunciante() {
		return totalAnunciante;
	}

	public void setTotalAnunciante(Integer totalAnunciante) {
		this.totalAnunciante = totalAnunciante;
	}

	public Integer getTotalFreelancer() {
		return totalFreelancer;
	}

	public void setTotalFreelancer(Integer totalFreelancer) {
		this.totalFreelancer = totalFreelancer;
	}

	public Integer getTotalInscricao() {
		return totalInscricao;
	}

	public void setTotalInscricao(Integer totalInscricao) {
		this.totalInscricao = totalInscricao;
	}

	public Integer getTotalContratacao() {
		return totalContratacao;
	}

	public void setTotalContratacao(Integer totalContratacao) {
		this.totalContratacao = totalContratacao;
	}

	@Override
	public String toString() {
		return "ViewConsolidadoFreelaSearch [id=" + id + ", totalAnuncio=" + totalAnuncio + ", totalAnunciante=" + totalAnunciante + ", totalFreelancer="
				+ totalFreelancer + ", totalInscricao=" + totalInscricao + ", totalContratacao=" + totalContratacao + "]";
	}

}
