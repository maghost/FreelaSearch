package org.freelasearch.filters;

import java.io.Serializable;

public class FiltroCategoria extends FiltroFreelaSearch implements Serializable {

	private static final long serialVersionUID = -588640845138718535L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
