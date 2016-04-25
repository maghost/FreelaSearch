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

	@OneToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;

	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Mensagem() {
		super();
	}

	public Mensagem(Integer id, String conteudo, Date dataEnvio, Chat chat, Usuario usuario) {
		super();
		this.id = id;
		this.conteudo = conteudo;
		this.dataEnvio = dataEnvio;
		this.chat = chat;
		this.usuario = usuario;
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

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", conteudo=" + conteudo + ", dataEnvio=" + dataEnvio + ", chat=" + chat + ", usuario=" + usuario + "]";
	}

}
