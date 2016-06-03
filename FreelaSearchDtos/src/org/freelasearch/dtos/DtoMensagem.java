package org.freelasearch.dtos;

import java.io.Serializable;
import java.util.Date;

public class DtoMensagem implements Serializable {

	private static final long serialVersionUID = 6943569960398543886L;

	private Integer id;
	private String conteudo;
	private Date dataEnvio;
	private Integer status;
	private DtoUsuario usuarioRemetente;
	private DtoUsuario usuarioDestinatario;

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

	public DtoUsuario getUsuarioRemetente() {
		return usuarioRemetente;
	}

	public void setUsuarioRemetente(DtoUsuario usuarioRemetente) {
		this.usuarioRemetente = usuarioRemetente;
	}

	public DtoUsuario getUsuarioDestinatario() {
		return usuarioDestinatario;
	}

	public void setUsuarioDestinatario(DtoUsuario usuarioDestinatario) {
		this.usuarioDestinatario = usuarioDestinatario;
	}

}
