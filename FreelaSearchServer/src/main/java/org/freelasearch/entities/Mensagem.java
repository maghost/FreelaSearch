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
public class Mensagem extends BaseBean {

	private static final long serialVersionUID = 6538787599436245089L;

	@Id
	@GeneratedValue
	private Integer id;

	private String conteudo;

	@Column(name = "data_envio")
	private Date dataEnvio;

	@Column(columnDefinition = "TINYINT")
	private Integer status;

	@OneToOne
	@JoinColumn(name = "usuario_id_enviado_por")
	private Usuario usuarioRemetente;

	@OneToOne
	@JoinColumn(name = "usuario_id_recebido_por")
	private Usuario usuarioDestinatario;

	public Mensagem() {
		super();
	}

	public Mensagem(Integer id, String conteudo, Date dataEnvio, Usuario usuarioRemetente, Usuario usuarioDestinatario) {
		super();
		this.id = id;
		this.conteudo = conteudo;
		this.dataEnvio = dataEnvio;
		this.usuarioRemetente = usuarioRemetente;
		this.usuarioDestinatario = usuarioDestinatario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Usuario getUsuarioRemetente() {
		return usuarioRemetente;
	}

	public void setUsuarioRemetente(Usuario usuarioRemetente) {
		this.usuarioRemetente = usuarioRemetente;
	}

	public Usuario getUsuarioDestinatario() {
		return usuarioDestinatario;
	}

	public void setUsuarioDestinatario(Usuario usuarioDestinatario) {
		this.usuarioDestinatario = usuarioDestinatario;
	}

	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", conteudo=" + conteudo + ", dataEnvio=" + dataEnvio + ", status=" + status + ", usuarioRemetente=" + usuarioRemetente + ", usuarioDestinatario=" + usuarioDestinatario + "]";
	}

}
