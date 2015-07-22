package com.programgyar.home.domain;

import java.util.Date;

public class LogRecord extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1119870088676878081L;
	protected Date date;
	protected String message = "";

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}