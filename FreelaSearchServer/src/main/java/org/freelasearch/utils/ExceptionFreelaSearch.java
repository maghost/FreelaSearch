package org.freelasearch.utils;

import org.apache.commons.lang3.StringEscapeUtils;

public class ExceptionFreelaSearch extends RuntimeException {

	private static final long serialVersionUID = -4589352281812485737L;

	public ExceptionFreelaSearch(String message) {
		super(StringEscapeUtils.escapeHtml4(message));
	}

}
