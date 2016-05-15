package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroCategoria extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = -588640845138718535L;

	private Integer id;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
