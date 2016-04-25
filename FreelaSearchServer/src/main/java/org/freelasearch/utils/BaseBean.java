package org.freelasearch.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.log4j.Logger;

public abstract class BaseBean implements Serializable {

	private static final long serialVersionUID = 1642535368703763753L;

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	protected <T> String printSpecificFieldFromBeanCollection(Collection<T> collection, String fieldName) {
		if (collection.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder("{");

		Iterator<T> it = collection.iterator();
		while (it.hasNext()) {
			T bean = it.next();

			try {
				Field field = bean.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);

				sb.append(field.get(bean));
			} catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
				Logger.getLogger(this.getClass().getSimpleName()).warn("Warning while printing bean", ex);
			}

			if (it.hasNext()) {
				sb.append(", ");
			}
		}

		return sb.append("}").toString();
	}

	// @Override
	// public String toString() {
	// return ToStringBuilder.reflectionToString(this,
	// ToStringStyle.MULTI_LINE_STYLE);
	// }

}
