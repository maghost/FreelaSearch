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
public class Anunciante extends BaseBean {

	private static final long serialVersionUID = -4421509167309962093L;

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@OneToMany(mappedBy = "anunciante", fetch = FetchType.LAZY)
	private List<Anuncio> anuncios;

	public Anunciante() {
	}

	public Anunciante(Integer id, Usuario usuario, List<Anuncio> anuncios) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.anuncios = anuncios;
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

	public List<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	@Override
	public String toString() {
		return "Anunciante [id=" + id + ", usuario=" + usuario + ", anuncios=" + printSpecificFieldFromBeanCollection(anuncios, "titulo") + "]";
	}

}
