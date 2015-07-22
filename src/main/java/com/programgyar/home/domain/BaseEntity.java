package com.programgyar.home.domain;

import java.io.Serializable;

public class BaseEntity implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5990671900692077757L;
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
