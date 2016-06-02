package org.freelasearch.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
@Table(name = "anuncio")
public class Anuncio extends BaseBean {

	private static final long serialVersionUID = 6052687250273171743L;

	@Id
	@GeneratedValue
	private Integer id;

	private String titulo;

	private String descricao;

	private Date data;

	@Column(columnDefinition = "TINYINT")
	private Integer status;

	private String cidade;

	private String estado;

	@OneToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@OneToOne
	@JoinColumn(name = "anunciante_id")
	private Anunciante anunciante;

	@OneToMany(mappedBy = "anuncio", fetch = FetchType.LAZY)
	private List<Inscricao> inscricoes;

	public Anuncio() {
	}

	public Anuncio(Integer id, String titulo, String descricao, Date data, Integer status, Categoria categoria, String cidade, String estado, Anunciante anunciante,
			List<Inscricao> inscricoes) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
		this.status = status;
		this.categoria = categoria;
		this.cidade = cidade;
		this.estado = estado;
		this.anunciante = anunciante;
		this.inscricoes = inscricoes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

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

	public Anunciante getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(Anunciante anunciante) {
		this.anunciante = anunciante;
	}

	public List<Inscricao> getInscricoes() {
		return inscricoes;
	}

	public void setInscricoes(List<Inscricao> inscricoes) {
		this.inscricoes = inscricoes;
	}

	@Override
	public String toString() {
		return "Anuncio [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", data=" + data + ", status=" + status + ", categoria=" + categoria + ", cidade="
				+ cidade + ", estado=" + estado + ", anunciante=" + anunciante + ", inscricoes=" + printSpecificFieldFromBeanCollection(inscricoes, "id") + "]";
	}

}
